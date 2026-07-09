import type { Metadata } from "next";
import Link from "next/link";
import { notFound } from "next/navigation";
import {
  getArticleBySlug,
  getArticleSlugs,
  formatDate,
} from "@/lib/articles";

type Params = { slug: string };

export function generateStaticParams(): Params[] {
  return getArticleSlugs().map((slug) => ({ slug }));
}

export async function generateMetadata({
  params,
}: {
  params: Params;
}): Promise<Metadata> {
  const article = await getArticleBySlug(params.slug);
  if (!article) return { title: "Article not found" };
  return {
    title: article.title,
    description: article.excerpt,
  };
}

export default async function ArticlePage({ params }: { params: Params }) {
  const article = await getArticleBySlug(params.slug);
  if (!article) notFound();

  return (
    <article className="mx-auto max-w-3xl px-4 py-12">
      <Link
        href="/articles"
        className="text-sm font-medium text-brand-600 hover:text-brand-700"
      >
        &larr; All articles
      </Link>

      <div className="mt-6 flex items-center gap-3 text-xs font-medium text-gray-500">
        <span className="rounded-full bg-brand-50 px-2.5 py-1 text-brand-700">
          {article.category}
        </span>
        <time dateTime={article.date}>{formatDate(article.date)}</time>
        <span>&middot;</span>
        <span>{article.author}</span>
      </div>

      <h1 className="mt-4 text-3xl font-extrabold tracking-tight text-gray-900 sm:text-4xl">
        {article.title}
      </h1>
      <p className="mt-4 text-lg text-gray-600">{article.excerpt}</p>

      <hr className="my-8 border-gray-100" />

      <div
        className="prose max-w-none"
        dangerouslySetInnerHTML={{ __html: article.contentHtml }}
      />
    </article>
  );
}
