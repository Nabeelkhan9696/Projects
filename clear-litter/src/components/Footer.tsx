export default function Footer() {
  const year = new Date().getFullYear();
  return (
    <footer className="mt-20 border-t border-gray-100 bg-gray-50">
      <div className="mx-auto max-w-5xl px-4 py-8 text-sm text-gray-500">
        <p className="font-semibold text-gray-700">Clear Litter</p>
        <p className="mt-1 max-w-xl">
          Independent reviews, buying guides, and daily articles about clear,
          crystal, and low-tracking cat litter. We may earn a commission from
          links on this site.
        </p>
        <p className="mt-4">&copy; {year} Clear Litter. All rights reserved.</p>
      </div>
    </footer>
  );
}
