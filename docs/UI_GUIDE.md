# Multi-Device UI (TV + TV Box + Mobile)

## Orientation
- **Default orientation**: LANDSCAPE across the app.
- **Mobile**: Keep landscape as default, but layouts must remain responsive and usable on smaller screens.

## Targets

### Android TV / TV Box
- **Launcher**: `LEANBACK_LAUNCHER` enabled.
- **Navigation**: D-pad focus system (`FocusRequester`, `focusable()`, clear visual focus states).
- **UX**: 10-foot UI spacing, larger typography, and large hit targets.

### Mobile
- **UX**: Touch-first interactions (click targets >= 48dp).
- **Responsive Layout**:
    - Grids collapse to fewer columns.
    - Side panels become bottom sheets or full-screen pages.

## Layout Strategy
- **Shared Screens**: Use shared screen containers for route and state logic.
- **Adaptive Components**: Split UI into device-specific content composables:
    - `ContentTv(...)`
    - `ContentMobile(...)`
- **Breakpoints**: Use `WindowSizeClass` or screen width breakpoints to switch layouts.
- **Constraints**: Never hardcode pixel sizes; always use `dp`/`sp` and adaptive grids.
