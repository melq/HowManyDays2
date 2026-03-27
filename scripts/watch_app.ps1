# watch_app.ps1

# Robust Path Resolution
$ScriptPath = $MyInvocation.MyCommand.Path
$ProjectRoot = Split-Path (Split-Path $ScriptPath -Parent) -Parent
Set-Location $ProjectRoot
Write-Host "Working Directory: $(Get-Location)"

$WatcherSource = Join-Path $ProjectRoot "app\src"
$Filter = "*.*" 
$DebounceSeconds = 2
$LastRun = [DateTime]::MinValue

# Setup FileSystemWatcher
if (-not (Test-Path $WatcherSource)) {
    Write-Error "Source directory not found: $WatcherSource"
    exit 1
}

$Watcher = New-Object System.IO.FileSystemWatcher
$Watcher.Path = $WatcherSource
$Watcher.Filter = $Filter
$Watcher.IncludeSubdirectories = $true
$Watcher.EnableRaisingEvents = $true

Write-Host "Watching for changes in $WatcherSource..."
Write-Host "Press Ctrl+C to stop."

try {
    # Invoke run_app.ps1
    & "$PSScriptRoot\run_app.ps1"
    while ($true) {
        # Wait for a change event (Timeout 1000ms to allow loop to check other things if needed)
        $Result = $Watcher.WaitForChanged([System.IO.WatcherChangeTypes]::All, 1000)
        
        if ($Result.TimedOut) {
            continue
        }

        # Change detected
        $FullPath = $Result.Name
        $ChangeType = $Result.ChangeType
        
        # Debounce
        $Now = Get-Date
        if (($Now - $LastRun).TotalSeconds -gt $DebounceSeconds) {
            $LastRun = $Now
            Write-Host "`nChange detected ($ChangeType): $FullPath"
            Write-Host "Triggering build & run..."
            
            # Invoke run_app.ps1
            & "$PSScriptRoot\run_app.ps1"
            
            Write-Host "Waiting for next change..."
        }
    }
}
finally {
    $Watcher.Dispose()
    Write-Host "Watcher stopped."
}
