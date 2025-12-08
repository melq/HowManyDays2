# run_app.ps1

# Configuration
$EmulatorName = "Pixel_8_API_35"
$SdkPath = "$env:LOCALAPPDATA\Android\Sdk"
$EmulatorPath = "$SdkPath\emulator\emulator.exe"
$JavaHome = "C:\Program Files\Android\Android Studio\jbr"
$PackageName = "com.github.melq.howmanydays"
$MainActivity = "$PackageName/.MainActivity"

# Set JAVA_HOME
$env:JAVA_HOME = $JavaHome
Write-Host "Set JAVA_HOME to $JavaHome"

# Move to project root (Robust way using script location)
$ScriptPath = $MyInvocation.MyCommand.Path
$ProjectRoot = Split-Path (Split-Path $ScriptPath -Parent) -Parent
Set-Location $ProjectRoot
Write-Host "Working Directory: $(Get-Location)"

# Check for active devices
Write-Host "Checking for active devices..."
$devices = adb devices
$deviceFound = $devices -match "\tdevice"

if (-not $deviceFound) {
    Write-Host "No active device found."
    
    # Validate Emulator Path
    if (-not (Test-Path $EmulatorPath)) {
        Write-Error "Emulator not found at $EmulatorPath"
        exit 1
    }

    # Start Emulator
    Write-Host "Starting Emulator: $EmulatorName..."
    Start-Process -FilePath $EmulatorPath -ArgumentList "-avd $EmulatorName -netdelay none -netspeed full" -NoNewWindow
    
    # Wait for Boot
    Write-Host "Waiting for device to be ready..."
    while (($null -eq (adb devices | Select-String "emulator")) -or ($null -eq (adb shell getprop sys.boot_completed | Select-String "1"))) {
        Write-Host -NoNewline "."
        Start-Sleep -Seconds 2
    }
    Write-Host "`nDevice Connected and Booted."
}
else {
    Write-Host "Active device found. Skipping emulator launch."
}

# Build and Install
Write-Host "Building and Installing Debug APK..."
.\gradlew.bat installDebug

if ($LASTEXITCODE -eq 0) {
    # Launch App
    Write-Host "Launching App..."
    adb shell am start -n $MainActivity
    Write-Host "Done!"
}
else {
    Write-Error "Build failed."
}
