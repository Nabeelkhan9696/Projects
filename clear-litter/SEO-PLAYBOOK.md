# Clear Litter — SEO Playbook

Standing strategy for the daily article pipeline. Every article published to
**www.clearlitter.com/blogs/news** follows these rules. The goal is not traffic
for its own sake — it's **orders**: rank for queries cat owners search right
before (or right after) buying litter, and route that intent to product pages.

## The store (link targets)

| Product | URL | Price | Angle |
| --- | --- | --- | --- |
| Unscented Crystal Cat Litter, 8 lb | `/products/unscented-crystal-cat-litter-8lb` | $23.99 | Flagship; scent-sensitive cats |
| Lavender Crystal Cat Litter, 8 lb | `/products/lavender-crystal-cat-litter` | $23.99 | Best-selling scented |
| Citrus Breeze Crystal Cat Litter, 8 lb | `/products/citrus-breeze-crystal-clear-litter` | $23.99 | Fresh-scent alternative |
| Health Monitoring Cat Litter, 8 lb | `/products/health-monitoring-cat-litter` | $26.99 | Unique differentiator — color-changing UTI/kidney detection. Highest margin, lowest keyword competition. Push hard. |
| Variety Pack, 3 × 8 lb | `/products/crystal-cat-litter-variety-pack-3-8-lb-unscented-lavender-citrus` | $59.99 | AOV raiser — pitch in "which scent" and stock-up contexts. ~3-month supply for one cat. |

Sitewide offers to mention where natural: **free shipping over $35** and the
**SUBSCRIBE15** code (15% off).

## Content strategy

1. **Bottom-of-funnel first.** Prioritize queries with purchase intent
   ("best crystal cat litter for odor", "does crystal litter work for multiple
   cats") over generic cat content. One order from 50 visits beats zero orders
   from 5,000.
2. **Own the health-monitoring niche.** Almost nobody writes about
   color-changing litter. Queries like "cat litter that detects UTI" are
   low-volume but nearly 100% purchase intent and we have the product.
3. **Answer real questions completely.** Google rewards pages that end the
   search. Every article should leave nothing to click back for.
4. **Internal links are the point.** 2–4 contextual product links per article,
   placed where the reader has just been given a reason to want the product —
   never a bare "buy here."
5. **Interlink articles.** Each new article links to 1–2 previous articles;
   periodically add links from old articles to new ones.

## On-page rules (every article)

- **Title (H1 + SEO title):** ≤ 60 chars, target keyword near the front,
  a reason to click (number, year, benefit, or question).
- **Meta description:** 140–155 chars, includes keyword, ends with an implicit
  promise of the answer.
- **Handle/URL:** short, keyword-only (e.g. `crystal-litter-odor-control`),
  no stop-words.
- **Structure:** H2s phrased as the sub-questions people actually search
  (they win featured snippets). Answer each H2's question in the first
  sentence under it, then elaborate.
- **Length:** 900–1,400 words. Long enough to be complete, short enough to
  stay dense.
- **FAQ block:** end with 3–4 real questions + concise answers (People-Also-Ask
  targets).
- **FAQPage schema:** append a `<script type="application/ld+json">` block at
  the very end of the body HTML containing schema.org `FAQPage` markup that
  mirrors the FAQ block (same questions, plain-text answers). This makes the
  article eligible for FAQ rich results.
- **Clean HTML only:** body must be semantic markup only — `h2/h3/p/ul/ol/
  table/blockquote/a/strong/em` with no class attributes, no wrapper divs,
  no styles. Never paste HTML exported from another app.
- **Tags:** 4–6, reuse the established set (`crystal litter`,
  `silica gel litter`, `cat litter guide`, `cat care`, `odor control`,
  `cat health`) plus 1 article-specific tag.
- **Author:** "Clear Litter Team".
- **Tone:** helpful expert, plain English, honest about trade-offs (honesty
  converts better and earns links). Never invent studies, statistics, or vet
  quotes. Product claims must match the actual product descriptions.

## Cadence & pipeline

- **One article per day**, published to the News blog
  (`gid://shopify/Blog/105530785826`) via the Shopify Admin API.
- Topics come from `content/keyword-queue.md`, top unpublished row first.
  Reorder freely when seasonality or store data suggests it.
- After publishing: archive the article as markdown in `content/published/`
  (frontmatter includes the Shopify article GID + live URL), mark the queue
  row done, commit and push.
- **Never republish or duplicate a topic** already in `content/published/`
  or already live on the blog.
- Weekly (every 7th run): instead of only writing, also review — check which
  older articles need internal links to newer ones, and refresh one old
  article's meta if it underperforms.

## Measurement

Watch in Shopify admin: search traffic landing on `/blogs/news/*` and
conversion paths from blog → product. When an article ranks, consider a
follow-up targeting the adjacent long-tail. Suggest to the owner: connect
Google Search Console to www.clearlitter.com if not already done — it's the
single most useful (free) data source for this pipeline.
