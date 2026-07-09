import Link from "next/link";
import { ArticleMeta, formatDate } from "@/lib/articles";

export default function ArticleCard({ article }: { article: ArticleMeta }) {
  return (
    <article className="group rounded-xl border border-gray-100 bg-white p-6 transition hover:border-brand-100 hover:shadow-sm">
      <div className="flex items-center gap-3 text-xs font-medium text-gray-500">
        <span className="rounded-full bg-brand-50 px-2.5 py-1 text-brand-700">
          {article.category}
        </span>
        <time dateTime={article.date}>{formatDate(article.date)}</time>
      </div>
      <h2 className="mt-3 text-xl font-semibold text-gray-900 group-hover:text-brand-700">
        <Link href={`/articles/${article.slug}`}>{article.title}</Link>
      </h2>
      <p className="mt-2 text-sm leading-relaxed text-gray-600">
        {article.excerpt}
      </p>
      <Link
        href={`/articles/${article.slug}`}
        className="mt-4 inline-block text-sm font-medium text-brand-600 hover:text-brand-700"
      >
        Read more &rarr;
      </Link>
    </article>
  );
}
