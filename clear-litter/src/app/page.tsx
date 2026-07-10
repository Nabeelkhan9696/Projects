import Link from "next/link";
import { getAllArticles } from "@/lib/articles";
import ArticleCard from "@/components/ArticleCard";

export default function Home() {
  const articles = getAllArticles();
  const [featured, ...rest] = articles;

  return (
    <div className="mx-auto max-w-5xl px-4">
      {/* Hero */}
      <section className="py-16 text-center">
        <p className="text-sm font-semibold uppercase tracking-wide text-brand-600">
          Clearer litter, happier cats
        </p>
        <h1 className="mx-auto mt-3 max-w-3xl text-4xl font-extrabold tracking-tight text-gray-900 sm:text-5xl">
          Reviews & guides for clear, crystal, and low-tracking cat litter
        </h1>
        <p className="mx-auto mt-5 max-w-2xl text-lg text-gray-600">
          We test and compare the litters that keep your home fresh and your cat
          comfortable — plus a fresh article every day.
        </p>
        <div className="mt-8 flex justify-center gap-3">
          <Link
            href="/articles"
            className="rounded-lg bg-brand-600 px-5 py-2.5 text-sm font-semibold text-white transition hover:bg-brand-700"
          >
            Browse articles
          </Link>
        </div>
      </section>

      {articles.length === 0 ? (
        <p className="py-12 text-center text-gray-500">
          No articles yet. Add your first Markdown file in{" "}
          <code className="rounded bg-gray-100 px-1 py-0.5">
            content/articles
          </code>
          .
        </p>
      ) : (
        <>
          {/* Featured */}
          {featured && (
            <section className="mb-12">
              <h2 className="mb-4 text-sm font-semibold uppercase tracking-wide text-gray-400">
                Latest
              </h2>
              <ArticleCard article={featured} />
            </section>
          )}

          {/* Recent grid */}
          {rest.length > 0 && (
            <section className="pb-8">
              <h2 className="mb-4 text-sm font-semibold uppercase tracking-wide text-gray-400">
                More articles
              </h2>
              <div className="grid gap-6 sm:grid-cols-2">
                {rest.map((article) => (
                  <ArticleCard key={article.slug} article={article} />
                ))}
              </div>
            </section>
          )}
        </>
      )}
    </div>
  );
}
