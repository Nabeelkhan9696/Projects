import Link from "next/link";

export default function Header() {
  return (
    <header className="border-b border-gray-100 bg-white/80 backdrop-blur sticky top-0 z-10">
      <div className="mx-auto flex max-w-5xl items-center justify-between px-4 py-4">
        <Link href="/" className="flex items-center gap-2">
          <span className="inline-block h-6 w-6 rounded-full bg-brand-500" aria-hidden />
          <span className="text-lg font-bold tracking-tight text-gray-900">
            Clear Litter
          </span>
        </Link>
        <nav className="flex items-center gap-6 text-sm font-medium text-gray-600">
          <Link href="/" className="hover:text-brand-600">
            Home
          </Link>
          <Link href="/articles" className="hover:text-brand-600">
            Articles
          </Link>
        </nav>
      </div>
    </header>
  );
}
