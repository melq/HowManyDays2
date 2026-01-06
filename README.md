https://github.com/melq/HowManyDays をJetPackComposeで作り直してます。

## 開発環境と自動化ツール

開発効率化のために、PowerShellスクリプトとVS Codeタスクを用意しています。
Windows環境での開発を想定しています。

### 自動監視・リロード (推奨)
ファイルの変更を検知して、自動的にビルド・インストール・実行を行います。

**使用方法:**
VS Codeで `Ctrl + Shift + B` を押し、タスク「**Watch App (Auto Reload)**」を選択してください。

### スクリプト一覧 (`scripts/`)
- `run_app.ps1`: アプリのビルド・インストール・起動をワンストップで行います。エミュレータが起動していない場合は自動的に起動します。
- `watch_app.ps1`: ソースフォルダを監視し、変更があるたびに `run_app.ps1` を実行します。
