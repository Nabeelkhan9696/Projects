# Clear Litter 🐾

A content website about **clear, crystal, and low-tracking cat litter** — product
reviews, buying guides, and a fresh article every day.

Built with [Next.js](https://nextjs.org/) (App Router) and Markdown. Write an
article by dropping a `.md` file into `content/articles/` — no code required.

## Tech stack

- **Next.js 14** (App Router, React Server Components)
- **TypeScript**
- **Tailwind CSS** for styling
- **Markdown** articles via `gray-matter` + `remark`

## Getting started

```bash
cd clear-litter
npm install
npm run dev
```

Then open <http://localhost:3000>.

Other scripts:

```bash
npm run build   # production build
npm run start   # serve the production build
npm run lint    # lint
```

## Project structure

```
clear-litter/
├── content/
│   └── articles/            # ← your Markdown articles live here
├── src/
│   ├── app/
│   │   ├── page.tsx         # Home page
│   │   ├── articles/
│   │   │   ├── page.tsx     # Article index
│   │   │   └── [slug]/      # Individual article pages
│   │   └── layout.tsx       # Shared header/footer + metadata
│   ├── components/          # Header, Footer, ArticleCard
│   └── lib/
│       └── articles.ts      # Loads & renders Markdown
└── ...config files
```

## Writing a daily article

1. Create a new file in `content/articles/`, e.g. `best-crystal-litter-2026.md`.
2. Add frontmatter and your content:

```markdown
---
title: "Best Crystal Cat Litters of 2026, Ranked"
date: "2026-07-10"
excerpt: "A one-sentence summary that shows up on cards and in search results."
category: "Reviews"
author: "Clear Litter Team"
---

Your article body in **Markdown**. Headings, lists, tables, quotes, and links
all work out of the box.
```

3. Save. The article appears automatically on the home page and `/articles`,
   sorted by `date` (newest first). The filename becomes the URL slug, e.g.
   `/articles/best-crystal-litter-2026`.

### Frontmatter fields

| Field | Required | Notes |
| --- | --- | --- |
| `title` | Yes | Article headline |
| `date` | Yes | `YYYY-MM-DD`; used for sorting and display |
| `excerpt` | Recommended | Shown on cards and used as meta description |
| `category` | Optional | e.g. `Reviews`, `Guides`, `Tips` (defaults to `General`) |
| `author` | Optional | Defaults to `Clear Litter Team` |

## Deploying

This is a standard Next.js app and deploys cleanly to
[Vercel](https://vercel.com/) — set the project's **root directory** to
`clear-litter` and it builds automatically.

## Roadmap ideas

- Category / tag pages
- Author profiles
- Newsletter signup
- Product comparison tables with affiliate links
- RSS feed for daily articles
