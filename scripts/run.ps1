$ErrorActionPreference = "Stop"
Set-Location (Resolve-Path (Join-Path $PSScriptRoot ".."))
New-Item -ItemType Directory -Path "out" -Force | Out-Null
$sources = @(Get-ChildItem -Path "src/main/java" -Filter "*.java" -Recurse -File | ForEach-Object { $_.FullName })
if ($sources.Count -eq 0) { throw "No Java source files found." }
& javac -encoding UTF-8 -d out @sources
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
& java -cp out kz.restordering.app.Main @args
exit $LASTEXITCODE
