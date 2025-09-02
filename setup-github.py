import os
from datetime import datetime

def create_github_deployment_files():
    # Create .github directory structure
    github_dirs = [
        ".github/workflows",
        ".github/ISSUE_TEMPLATE"
    ]
    
    for dir_path in github_dirs:
        os.makedirs(dir_path, exist_ok=True)
        print(f"📁 Created directory: {dir_path}")

    # Create GitHub Actions workflow
    android_ci_workflow = """name: Android CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout code
      uses: actions/checkout@v3

    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Setup Android SDK
      uses: android-actions/setup-android@v2

    - name: Make gradlew executable
      run: chmod +x ./gradlew

    - name: Build APK
      run: ./gradlew assembleDebug

    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app-debug-apk
        path: app/build/outputs/apk/debug/app-debug.apk

  release-build:
    runs-on: ubuntu-latest
    if: startsWith(github.ref, 'refs/tags/')
    
    steps:
    - name: Checkout code
      uses: actions/checkout@v3

    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Setup Android SDK
      uses: android-actions/setup-android@v2

    - name: Make gradlew executable
      run: chmod +x ./gradlew

    - name: Build Release APK
      run: |
        ./gradlew assembleRelease
        echo "Release APK built successfully"

    - name: Upload Release APK
      uses: actions/upload-artifact@v3
      with:
        name: app-release-apk
        path: app/build/outputs/apk/release/app-release.apk
"""
    
    with open(".github/workflows/android.yml", "w") as f:
        f.write(android_ci_workflow)
    print("📄 Created: .github/workflows/android.yml")

    # Create GitHub Pages deployment workflow
    pages_workflow = """name: Deploy to GitHub Pages

on:
  push:
    branches: [ main ]
  workflow_dispatch:

permissions:
  contents: read
  pages: write
  id-token: write

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v3
        
      - name: Setup Pages
        uses: actions/configure-pages@v2
        
      - name: Build with Jekyll
        uses: actions/jekyll-build-pages@v1
        with:
          source: ./docs
          destination: ./_site
          
      - name: Upload artifact
        uses: actions/upload-pages-artifact@v1

  deploy:
    environment:
      name: github-pages
      url: ${{ steps.deployment.outputs.page_url }}
    runs-on: ubuntu-latest
    needs: build
    steps:
      - name: Deploy to GitHub Pages
        id: deployment
        uses: actions/deploy-pages@v1
"""
    
    with open(".github/workflows/deploy-pages.yml", "w") as f:
        f.write(pages_workflow)
    print("📄 Created: .github/workflows/deploy-pages.yml")

    # Create release configuration
    release_config = """changelog:
  exclude:
    labels:
      - ignore-for-release
  categories:
    - title: Breaking Changes
      labels:
        - breaking-change
    - title: New Features
      labels:
        - feature
    - title: Bug Fixes
      labels:
        - bug
    - title: Documentation
      labels:
        - documentation
    - title: Other Changes
      labels:
        - enhancement
        - dependencies
"""
    
    with open(".github/release.yml", "w") as f:
        f.write(release_config)
    print("📄 Created: .github/release.yml")

    # Create issue templates
    bug_report = """---
name: Bug Report
about: Create a report to help us improve
title: '[BUG] '
labels: bug
assignees: ''

---

**Describe the bug**
A clear and concise description of what the bug is.

**To Reproduce**
Steps to reproduce the behavior:
1. Go to '...'
2. Click on '....'
3. Scroll down to '....'
4. See error

**Expected behavior**
A clear and concise description of what you expected to happen.

**Screenshots**
If applicable, add screenshots to help explain your problem.

**Device Information:**
 - Device: [e.g. Samsung Galaxy S21]
 - OS: [e.g. Android 12]
 - App Version [e.g. 1.0.0]

**Additional context**
Add any other context about the problem here.
"""
    
    with open(".github/ISSUE_TEMPLATE/bug_report.md", "w") as f:
        f.write(bug_report)
    print("📄 Created: .github/ISSUE_TEMPLATE/bug_report.md")

    feature_request = """---
name: Feature Request
about: Suggest an idea for this project
title: '[FEATURE] '
labels: enhancement
assignees: ''

---

**Is your feature request related to a problem? Please describe.**
A clear and concise description of what the problem is. Ex. I'm always frustrated when [...]

**Describe the solution you'd like**
A clear and concise description of what you want to happen.

**Describe alternatives you've considered**
A clear and concise description of any alternative solutions or features you've considered.

**Additional context**
Add any other context or screenshots about the feature request here.
"""
    
    with open(".github/ISSUE_TEMPLATE/feature_request.md", "w") as f:
        f.write(feature_request)
    print("📄 Created: .github/ISSUE_TEMPLATE/feature_request.md")

    # Create .gitattributes
    gitattributes = """# Auto detect text files and perform LF normalization
* text=auto

# These files are text and should be normalized
*.java text
*.kt text
*.xml text
*.gradle text
*.md text
*.properties text
*.txt text
*.yml text
*.yaml text

# These files are binary and should not be modified
*.apk binary
*.aab binary
*.png binary
*.jpg binary
*.jpeg binary
*.gif binary
*.ico binary
*.jar binary
*.keystore binary
"""
    
    with open(".gitattributes", "w") as f:
        f.write(gitattributes)
    print("📄 Created: .gitattributes")

    # Create docs directory and APK download page
    os.makedirs("docs", exist_ok=True)
    print("📁 Created directory: docs")

    apk_download_page = """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Construction Labour Management APK Download</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2196F3;
        }
        .download-btn {
            display: inline-block;
            background-color: #2196F3;
            color: white;
            padding: 15px 30px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            margin: 10px 0;
        }
        .download-btn:hover {
            background-color: #0b7dda;
        }
        .instructions {
            background-color: #f9f9f9;
            padding: 15px;
            border-left: 4px solid #2196F3;
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Construction Labour Management App</h1>
        <p>Download the latest APK for Android devices:</p>
        
        <a href="../app/build/outputs/apk/debug/app-debug.apk" class="download-btn">
            Download APK (Debug)
        </a>
        
        <div class="instructions">
            <h3>Installation Instructions:</h3>
            <ol>
                <li>Download the APK file using the button above</li>
                <li>On your Android device, go to Settings > Security</li>
                <li>Enable "Unknown sources" (allow installation from unknown sources)</li>
                <li>Open the downloaded APK file to install the app</li>
                <li>Launch the app and enjoy!</li>
            </ol>
        </div>
        
        <h3>App Features:</h3>
        <ul>
            <li>Manage construction sites and labor</li>
            <li>Track attendance and payments</li>
            <li>Generate weekly reports</li>
            <li>Customer payment tracking</li>
        </ul>
        
        <p><strong>Note:</strong> This is a debug build for testing purposes.</p>
    </div>
</body>
</html>
"""
    
    with open("docs/index.html", "w") as f:
        f.write(apk_download_page)
    print("📄 Created: docs/index.html")

    # Create MIT License file
    current_year = datetime.now().year
    license_content = f"""MIT License

Copyright (c) {current_year} [Your Name]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
"""
    
    with open("LICENSE", "w") as f:
        f.write(license_content)
    print("📄 Created: LICENSE")

    # Update README.md with GitHub badges
    readme_content = """# Construction Labour Management Android App

[![Android CI](https://github.com/yourusername/ConstructionLabourManagementAndroid/actions/workflows/android.yml/badge.svg)](https://github.com/yourusername/ConstructionLabourManagementAndroid/actions/workflows/android.yml)
[![GitHub release](https://img.shields.io/github/v/release/yourusername/ConstructionLabourManagementAndroid)](https://github.com/yourusername/ConstructionLabourManagementAndroid/releases)

A comprehensive Android application for managing construction labor, sites, attendance, and payments.

## Features

- User authentication (login/register)
- Site management
- Labor management
- Attendance tracking
- Payment management
- Customer payment tracking
- Weekly reports

## Download APK

You can download the latest APK from the [Releases page](https://github.com/yourusername/ConstructionLabourManagementAndroid/releases) or from the [GitHub Actions artifacts](https://github.com/yourusername/ConstructionLabourManagementAndroid/actions).

## Building the Project

1. Clone the repository
2. Open in Android Studio
3. Build the project
4. Run on device/emulator or generate APK

## GitHub Actions

This project uses GitHub Actions to automatically build APKs on every push to the main branch. The built APKs can be found in the Actions tab.

## Technology Stack

- Kotlin
- Android SDK
- Room Database
- ViewBinding
- Material Design Components
- GitHub Actions for CI/CD

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
"""
    
    with open("README.md", "w") as f:
        f.write(readme_content)
    print("📄 Updated: README.md")

    print("\n✅ All GitHub deployment files and LICENSE have been successfully created!")
    print("📁 Files created:")
    print("   - .github/workflows/android.yml")
    print("   - .github/workflows/deploy-pages.yml")
    print("   - .github/release.yml")
    print("   - .github/ISSUE_TEMPLATE/bug_report.md")
    print("   - .github/ISSUE_TEMPLATE/feature_request.md")
    print("   - .gitattributes")
    print("   - docs/index.html")
    print("   - LICENSE")
    print("   - Updated README.md")
    print("\n🚀 Next steps:")
    print("   1. Replace '[Your Name]' in the LICENSE file with your actual name")
    print("   2. Replace 'yourusername' in README.md with your GitHub username")
    print("   3. Commit these files to your repository")
    print("   4. Push to GitHub")
    print("   5. GitHub Actions will automatically build your APK")

if __name__ == "__main__":
    print("🚀 Generating GitHub deployment files and LICENSE for Android project...")
    create_github_deployment_files()