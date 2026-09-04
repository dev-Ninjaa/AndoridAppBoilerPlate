# Release Guide for Aura APK

## Quick Release Process

### Option 1: Using Git Tags (Recommended)

This automatically creates a GitHub Release with the APK attached:

```bash
# 1. Commit your changes
git add .
git commit -m "Ready for release v1.0.0"

# 2. Create a version tag
git tag v1.0.0

# 3. Push the tag to GitHub
git push origin v1.0.0
```

**What happens automatically:**
- GitHub Actions builds the APK
- A new Release is created at `https://github.com/YOUR_USERNAME/aura/releases`
- The APK is automatically uploaded with installation instructions
- Users can download directly from the Releases page

### Option 2: Manual GitHub Release

1. Go to your repository on GitHub
2. Click **Releases** → **Draft a new release**
3. Click **Choose a tag** → Create a new tag (e.g., `v1.0.1`)
4. Fill in the release title (e.g., "Aura v1.0.1")
5. Click **Publish release**
6. The workflow will automatically build and attach the APK

### Option 3: Manual Workflow Trigger

1. Go to **Actions** tab
2. Select **Release APK to GitHub Releases**
3. Click **Run workflow**
4. Select branch and click **Run workflow**
5. A new release will be created with today's date

## Version Naming Convention

Use [Semantic Versioning](https://semver.org/):
- `v1.0.0` - Major release (breaking changes)
- `v1.1.0` - Minor release (new features)
- `v1.0.1` - Patch release (bug fixes)

## Where Users Download

Direct link: `https://github.com/YOUR_USERNAME/aura/releases`

Users can:
1. Click on the latest release
2. Download the APK file directly
3. Install on their Android device

## Testing Before Release

Before creating a release tag, test your build:

```bash
# Build locally
./gradlew assembleDebug

# Or trigger the regular build workflow
git push origin main
# Then download from Actions → Artifacts
```

## Updating the App Version

Update version info in `app/build.gradle.kts`:

```kotlin
defaultConfig {
    versionCode = 2        // Increment for each release
    versionName = "1.0.1"  // Match your tag version
}
```

## Troubleshooting

### Release not created
- Check **Actions** tab for errors
- Ensure `GITHUB_TOKEN` has write permissions (should be automatic)
- Verify the workflow file has `permissions: contents: write`

### APK not attached
- Check if the build step succeeded
- Look for the APK in `app/build/outputs/apk/debug/`
- Verify the workflow completed all steps

### How to delete a release
1. Go to Releases
2. Click the release to delete
3. Click **Delete** button
4. Optionally delete the associated tag:
   ```bash
   git tag -d v1.0.0
   git push origin :refs/tags/v1.0.0
   ```

## APK Installation for Users

Share this link with users:
```
https://github.com/YOUR_USERNAME/aura/releases/latest
```

They can:
1. Download the APK
2. Enable "Install from Unknown Sources" if prompted
3. Tap the APK to install
4. Open Aura app
