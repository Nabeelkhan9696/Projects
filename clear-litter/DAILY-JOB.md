# Daily Article Job — Runbook

Executed once per day by the scheduled Routine. Follow exactly; keep each run
focused and cheap.

## Steps

1. **Sync:** `git pull origin claude/clear-litter-repo-setup-x7sm3p` in
   `/home/user/Projects` (create/checkout the branch if needed).
2. **Pick topic:** open `content/keyword-queue.md`, take the top `queued` row.
   Cross-check `content/published/` and the live blog to be sure it isn't a
   duplicate; if it is, mark it `skipped (duplicate)` and take the next row.
3. **Write:** draft the article per `SEO-PLAYBOOK.md` (on-page rules section
   is mandatory: title ≤60 chars, meta 140–155 chars, question-form H2s,
   FAQ block, 2–4 contextual product links, 1–2 links to earlier articles,
   900–1,400 words, no invented facts).
4. **Publish:** create the article via Shopify Admin GraphQL
   (`articleCreate`) on blog `gid://shopify/Blog/105530785826`:
   - `isPublished: true`, author "Clear Litter Team"
   - body as clean HTML (h2/h3/p/ul/table/blockquote only, no classes/divs)
   - FAQPage JSON-LD `<script type="application/ld+json">` appended at the
     end of the body, mirroring the article's FAQ block
   - tags per playbook
   - handle = short keyword slug
   - summary = the meta description
5. **Verify:** query the article back; confirm it's published and note the
   live URL (`https://www.clearlitter.com/blogs/news/<handle>`).
6. **Archive:** save the article as
   `content/published/YYYY-MM-DD-<handle>.md` with frontmatter:
   `title, date, handle, shopify_gid, url, keyword, tags, meta_description`.
7. **Update queue:** set the row's status to `done YYYY-MM-DD` and append the
   live URL to the "Already live" list at the top.
8. **Commit & push:** message `Publish daily article: <title>`, push with
   `git push -u origin claude/clear-litter-repo-setup-x7sm3p`.
9. **Every 7th article** (rows 7, 14, 21, 28…): also do the weekly review from
   the playbook — add internal links from 1–2 older articles to newer ones via
   `articleUpdate`.

## Failure handling

- Shopify mutation fails → retry once; if still failing, archive the article
  locally with status `pending-publish`, commit it, and report the error.
- Queue empty → generate the next 30-row queue per the playbook strategy,
  commit it, and publish row 1 of the new queue.
- Never publish two articles in one day; if the previous day's run failed,
  publish only the oldest unpublished one.
