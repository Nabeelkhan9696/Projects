# Warda — Job Hunt

Warda ("rose" in Arabic) is a job hunt companion. This directory currently contains the sign-in page and a demo dashboard, built as a dependency-free static site.

## Pages

- `index.html` — sign-in page with demo authentication (client-side, for demo purposes only)
- `dashboard.html` — demo dashboard shown after a successful sign-in
- `styles.css` — shared styles

## Demo credentials

| Field    | Value             |
| -------- | ----------------- |
| Email    | `demo@warda.jobs` |
| Password | `Warda@2026`      |

There is also a **"Fill demo credentials"** button on the sign-in page.

## Run locally

No build step needed — open `index.html` in a browser, or serve the folder:

```bash
npx serve warda
```

## Notes

- Authentication is a client-side demo only (credentials are checked in the browser and a flag is stored in `sessionStorage`). Do not use this pattern for real authentication.
