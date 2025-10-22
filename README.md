# Minecraft City Builder (Forge 1.12 scaffold)

This repository is a starter scaffold for building Forge 1.12 mods focused on city building tools.

Quick setup (PowerShell):

```powershell
# download gradle wrapper and initialize workspace
./gradlew setupDecompWorkspace --refresh-dependencies

# build the mod jar
./gradlew build

# output jar can be found in build/libs/
```

Notes:
- This is a minimal scaffold: add code under `src/main/java` and resources under `src/main/resources`.
- The project targets Minecraft 1.12 with ForgeGradle. Adjust `build.gradle` minecraft.version as needed.

