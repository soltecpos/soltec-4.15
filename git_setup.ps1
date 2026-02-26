$ErrorActionPreference = "Stop"
$git = "C:\Program Files\Git\cmd\git.exe"
Set-Location "C:\Users\aux1\Documents\loquedejolatormenta"

# Set up global user info if not set
& $git config --global user.name "Soltec POS Admin"
& $git config --global user.email "admin@soltec.local"

# Initialize or reinitialize repository
& $git init

# Check the current status
& $git status

# Add everything
& $git add .

# Create the backup commit
& $git commit -m "Backup version 26021246: Última versión estable del código fuente."
