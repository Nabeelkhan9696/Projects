import type { Metadata } from "next";
import { getAllArticles } from "@/lib/articles";
import ArticleCard from "@/components/ArticleCard";

export const metadata: Metadata = {
  title: "Articles",
  description:
    "Every Clear Litter article — reviews, buying guides, and daily tips on clear and crystal cat litter.",
};

export default function ArticlesPage() {
  const articles = getAllArticles();

  return (
    <div className="mx-auto max-w-5xl px-4 py-12">
      <h1 className="text-3xl font-extrabold tracking-tight text-gray-900">
        All Articles
      </h1>
      <p className="mt-2 text-gray-600">
        {articles.length} article{articles.length === 1 ? "" : "s"} and counting.
      </p>

      {articles.length === 0 ? (
        <p className="mt-12 text-gray-500">
          No articles yet. Drop a Markdown file into{" "}
          <code className="rounded bg-gray-100 px-1 py-0.5">
            content/articles
          </code>{" "}
          to publish one.
        </p>
      ) : (
        <div className="mt-8 grid gap-6 sm:grid-cols-2">
          {articles.map((article) => (
            <ArticleCard key={article.slug} article={article} />
          ))}
        </div>
      )}
    </div>
  );
}
