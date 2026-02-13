# Set up variables
$workDir = "c:\Users\aux1\Documents\loquedejolatormenta"
$toolsDir = "$workDir\tools"
$jdkUrl = "https://github.com/adoptium/temurin8-binaries/releases/download/jdk8u402-b06/OpenJDK8U-jdk_x64_windows_hotspot_8u402b06.zip"
$antUrl = "https://dlcdn.apache.org/ant/binaries/apache-ant-1.10.14-bin.zip"
$cfrUrl = "https://github.com/leibnitz27/cfr/releases/download/0.152/cfr-0.152.jar"

# Create tools directory
if (-not (Test-Path -Path $toolsDir)) {
    New-Item -ItemType Directory -Path $toolsDir | Out-Null
    Write-Host "Created tools directory at $toolsDir"
}

# Function to download and extract
function Unzip-File {
    param (
        [string]$zipFile,
        [string]$destination
    )
    Write-Host "Extracting $zipFile to $destination..."
    Expand-Archive -Path $zipFile -DestinationPath $destination -Force
}

function Download-Tool {
    param (
        [string]$url,
        [string]$outputFile,
        [string]$name
    )
    Write-Host "Downloading $name..."
    Invoke-WebRequest -Uri $url -OutFile $outputFile
    Write-Host "$name downloaded."
}

# 1. JDK 8
$jdkZip = "$toolsDir\jdk8.zip"
Download-Tool -url $jdkUrl -outputFile $jdkZip -name "JDK 8"
Unzip-File -zipFile $jdkZip -destination $toolsDir
# Rename extracted folder for easier access (finding the directory that starts with jdk8)
$extractedJdk = Get-ChildItem -Path $toolsDir -Directory | Where-Object { $_.Name -like "jdk8*" } | Select-Object -First 1
if ($extractedJdk) {
    Rename-Item -Path $extractedJdk.FullName -NewName "jdk8" -ErrorAction SilentlyContinue
    Write-Host "JDK 8 setup complete."
}

# 2. Apache Ant
$antZip = "$toolsDir\ant.zip"
Download-Tool -url $antUrl -outputFile $antZip -name "Apache Ant"
Unzip-File -zipFile $antZip -destination $toolsDir
$extractedAnt = Get-ChildItem -Path $toolsDir -Directory | Where-Object { $_.Name -like "apache-ant*" } | Select-Object -First 1
if ($extractedAnt) {
    Rename-Item -Path $extractedAnt.FullName -NewName "ant" -ErrorAction SilentlyContinue
    Write-Host "Apache Ant setup complete."
}

# 3. CFR Decompiler
$cfrJar = "$toolsDir\cfr.jar"
Download-Tool -url $cfrUrl -outputFile $cfrJar -name "CFR Decompiler"
Write-Host "CFR Decompiler setup complete."

Write-Host "All tools installed successfully."
