export function escapeStringToHtml(content) {
  if (content == null) return content
  content = content.replace(/&lt;/g, '<');
  content = content.replace(/&gt;/g, '>');
  return content;
}

export function escapeHtmlToString(html) {
  if (html == null) return html
  html = html.replace(/</g, '&lt;');
  html = html.replace(/>/g, '&gt;');
  return html;
}
