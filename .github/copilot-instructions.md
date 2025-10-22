# AI Coding Agent Instructions - Minecraft City Builder

This file gives practical, actionable guidance for AI coding agents working on the Minecraft City Builder project. The repository currently targets Minecraft Java Edition 1.12 with a Forge-first approach (client + server mod support). Keep changes minimal and safe: prefer adding new modules and features rather than large refactors unless asked.

## Big picture

- Intended platform: Minecraft Java 1.12 (Forge mod recommended). Forge allows rich client-side UI (overlays, previews) and server-side authority when needed.
- Primary focus: creative city-building workflow tools (building aids, palette managers, stencil/façade tools, road/parcel generators). Avoid simulation-style features (traffic AI, complex economy) unless scoped as a separate subsystem.
- Separation of concerns: split UI/preview code (client) from authoritative placement/automation (server). Implement server-side "commands" and placement via Forge event hooks and use packet sync for client previews.

## Developer workflows (quick)

- Build system: Gradle with ForgeGradle (standard for 1.12). Expect `gradlew build` to produce a `build/libs/*.jar` mod file for manual install/run. Use PowerShell on Windows.

  Example (PowerShell):
  ```powershell
  ./gradlew setupDecompWorkspace --refresh-dependencies
  ./gradlew build
  ```

- Run: Use the Forge run tasks from Gradle or copy the built jar into a `mods/` folder of a Forge 1.12 instance.
- Tests: No test framework present yet — add unit tests for pure Java modules and small integration tasks where possible.

## Project-specific conventions

- Forge-first: Put shared logic in `src/main/java` common packages; put client-only rendering in `client` packages gated by `@SideOnly(Side.CLIENT)`.
- Small, focused modules: each tool should be its own package (e.g., `road`, `stencil`, `palette`) with clear API boundaries.
- Use JSON metadata for palettes, stencils and style profiles (easy to review and share).

## Integration points & external dependencies

- WorldEdit/FAWE: For server-side large edits prefer integrating with WorldEdit/FAWE where possible to leverage safe, async edits and undo support.
- Schematic formats: support `.schematic` and 1.12 structure NBT (normalize to internal voxel arrays for diffs/merges).

## Top recommended project ideas (Forge-first)

Below are short, prioritized ideas discovered during research. Each entry lists why it helps city-building and the minimal MVP to aim for.

1. Façade Stencil Assistant (Forge) — Capture a wall face as a 2D stencil and stamp it with correct block orientations.
	- Why: Repeats detailed façades quickly while preserving oriented blocks (stairs, slabs).
	- MVP: record face, rotate/mirror, paste with orientation mapping.

2. Holographic Assembly Sequencer (Forge client + server sync) — Split schematics into build steps with per-step hologram.
	- Why: Organizes team build tasks and prevents paste-overwrites.
	- MVP: load schematic, split by Y-layers, step-through preview.

3. Palette Manager + Auto-Restock (server plugin/Forge hybrid) — Capture palette counts, link to chests for hotbar restock.
	- Why: Keeps builders supplied and reduces inventory friction.
	- MVP: capture palette, create bin, pull from linked chest.

4. Procedural Road Spline Generator (server plugin + optional client preview) — Generate roads from control points with parcel subdivision.
	- Why: Speeds infrastructure layout and creates consistent lots.
	- MVP: place spline between markers, generate basic road cross-section.

    ### Key Systems

    1. **Control Point System**
    - Custom marker block for defining road path
    - Stores points in NBT data for persistence
    - Right-click to place, left-click to remove
    - Network sync for multiplayer coordination

    2. **Spline Generation**
    - Cubic spline interpolation between control points
    - Adjusts for terrain height with max slope limits
    - Generates cross-section perpendicular to path direction
    - Handles curves with proper block orientation

    3. **Road Profiles**
    - Configurable width, materials, features (sidewalks, curbs)
    - JSON-based profile storage for sharing/presets
    - Block orientation handling for stairs/slabs in details
    - Support for asymmetric profiles (medians, turning lanes)

    4. **Preview System**
    - Client-side particle effects for spline path
    - Ghost block preview for road surface
    - Updates in real-time as points are added/moved
    - Toggle between 2D plan view and 3D preview

    5. **Generation Process**
    - FAWE integration for async block placement
    - Terrain conforming with cut/fill calculation
    - Proper block updates for redstone/lighting
    - Undo support via WorldEdit history
    
    ### Integration Points
    - WorldEdit/FAWE API for efficient block operations
    - Forge event system for block placement/interaction
    - NBT storage for road profiles and markers
    - Client-server packet sync for previews

5. Schematic Diff & Merge Tool (server + optional client) — Diff two schematics or schematic vs world, visualize changes.
	- Why: Avoids accidental overwrites; supports collaborative merges.
	- MVP: load two schematics, show added/removed/changed blocks via overlay markers.

6. Curved Roof Generator (Forge) — Procedural roof curves using stairs/slabs and seam handling.
	- Why: Produces complex roofs faster and consistently.
	- MVP: generate a barrel or dome profile across a span; preview & commit.

7. Auto-Detail Orchestrator (Forge) — Rule-driven decoration passes (trim bands, window sills).
	- Why: Quickly add cohesive ornamentation across many buildings.
	- MVP: single style profile with edge-trim and preview/commit.

8. City Lighting Planner (server) — Lightmap heatmap and lamp placement suggestions to avoid mob spawns.
	- Why: Automates lighting balancing and aesthetics.
	- MVP: area scan, heatmap overlay, suggested lamp positions.

9. Persistent Invisible Scaffolding (Forge) — Per-player persistent scaffold overlays saved to disk.
	- Why: Keeps private build guides across sessions.
	- MVP: create scaffold layer visible only to owner, persist between logins.

10. Schematic Hologram Diff/Merge + Palette Integration — Combine diff/merge with per-step palettes and restock hints.
	- Why: End-to-end collaborative workflow from design to execution.
	- MVP: composite of items 2,3 and 5 scaled back to core interactions.

## Practical agent rules

- Read large files fully before editing. Favor edits that add files or small, localized changes.
- Ask these questions before major changes: targeted Minecraft version, Forge vs Fabric, server vs singleplayer priority, and desired license.
- When adding code: include minimal unit tests for pure logic and ensure no hard-coded file paths. Run `./gradlew build` to validate.

## Files to check first

- `build.gradle`, `settings.gradle`, `gradle.properties` (Forge workspace configuration)
- `src/main/java` and `src/main/resources` (mod code and resources)
- `README.md` and `.github/CODEOWNERS` (project policies)

---
If anything above is incorrect or you'd like Spigot-first instead of Forge, tell me and I'll pivot. Next I'll scaffold a minimal Forge 1.12 mod and add a README with PowerShell build/run commands.