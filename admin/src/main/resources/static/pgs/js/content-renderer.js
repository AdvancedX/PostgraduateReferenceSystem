(function (global) {
    'use strict';

    var allowedTags = {
        A: true, B: true, BLOCKQUOTE: true, BR: true, CODE: true, DEL: true,
        DIV: true, EM: true, FIGCAPTION: true, FIGURE: true, H1: true, H2: true,
        H3: true, H4: true, H5: true, H6: true, HR: true, I: true, IMG: true,
        LI: true, OL: true, P: true, PRE: true, S: true, SPAN: true, STRONG: true,
        TABLE: true, TBODY: true, TD: true, TH: true, THEAD: true, TR: true,
        U: true, UL: true
    };

    function escapeHtml(value) {
        return String(value == null ? '' : value)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
    }

    function safeUrl(value, image) {
        var url = String(value || '').trim().replace(/&amp;/g, '&');
        if (!url) return '';
        if (/^(https?:|mailto:|tel:)/i.test(url)) return url;
        if (image && /^(\/|\.\/|\.\.\/)/.test(url)) return url;
        if (!image && /^(\/|#|\.\/|\.\.\/)/.test(url)) return url;
        return '';
    }

    function renderInline(source) {
        var tokens = [];
        var tokenPrefix = '\uE000PGSTOKEN';
        var text = String(source == null ? '' : source);

        function stash(html) {
            var token = tokenPrefix + tokens.length + '\uE001';
            tokens.push(html);
            return token;
        }

        text = text.replace(/`([^`\n]+)`/g, function (_, code) {
            return stash('<code>' + escapeHtml(code) + '</code>');
        });
        text = text.replace(/\[([^\]\n]+)\]\(([^\s)]+)(?:\s+["']([^"']*)["'])?\)/g,
            function (whole, label, url, title) {
                var href = safeUrl(url, false);
                if (!href) return label + ' (' + url + ')';
                var titleAttr = title ? ' title="' + escapeHtml(title) + '"' : '';
                return stash('<a href="' + escapeHtml(href) + '" target="_blank" rel="noopener noreferrer"' + titleAttr + '>' + escapeHtml(label) + '</a>');
            });

        text = escapeHtml(text)
            .replace(/~~([^~\n]+)~~/g, '<del>$1</del>')
            .replace(/\*\*([^*\n]+)\*\*/g, '<strong>$1</strong>')
            .replace(/__([^_\n]+)__/g, '<strong>$1</strong>')
            .replace(/(^|[^*])\*([^*\n]+)\*(?!\*)/g, '$1<em>$2</em>')
            .replace(/(^|[^_])_([^_\n]+)_(?!_)/g, '$1<em>$2</em>');

        tokens.forEach(function (html, index) {
            text = text.split(tokenPrefix + index + '\uE001').join(html);
        });
        return text;
    }

    function splitTableRow(line) {
        var value = line.trim();
        if (value.charAt(0) === '|') value = value.slice(1);
        if (value.charAt(value.length - 1) === '|') value = value.slice(0, -1);
        return value.split('|').map(function (cell) { return cell.trim(); });
    }

    function isTableDivider(line) {
        var cells = splitTableRow(line);
        return cells.length > 0 && cells.every(function (cell) {
            return /^:?-{3,}:?$/.test(cell);
        });
    }

    function renderMarkdown(markdown) {
        var lines = String(markdown == null ? '' : markdown).replace(/\r\n?/g, '\n').split('\n');
        var html = [];
        var index = 0;

        while (index < lines.length) {
            var line = lines[index];
            if (!line.trim()) {
                index += 1;
                continue;
            }

            var fence = line.match(/^\s*```([^`]*)$/);
            if (fence) {
                var language = fence[1].trim().replace(/[^A-Za-z0-9_+-]/g, '');
                var codeLines = [];
                index += 1;
                while (index < lines.length && !/^\s*```\s*$/.test(lines[index])) {
                    codeLines.push(lines[index]);
                    index += 1;
                }
                if (index < lines.length) index += 1;
                html.push('<pre><code' + (language ? ' class="language-' + language + '"' : '') + '>' + escapeHtml(codeLines.join('\n')) + '</code></pre>');
                continue;
            }

            var heading = line.match(/^\s{0,3}(#{1,6})\s+(.+)$/);
            if (heading) {
                var level = heading[1].length;
                html.push('<h' + level + '>' + renderInline(heading[2]) + '</h' + level + '>');
                index += 1;
                continue;
            }

            if (/^\s{0,3}([-*_])(?:\s*\1){2,}\s*$/.test(line)) {
                html.push('<hr>');
                index += 1;
                continue;
            }

            if (line.indexOf('|') !== -1 && index + 1 < lines.length && isTableDivider(lines[index + 1])) {
                var headers = splitTableRow(line);
                index += 2;
                var rows = [];
                while (index < lines.length && lines[index].trim() && lines[index].indexOf('|') !== -1) {
                    rows.push(splitTableRow(lines[index]));
                    index += 1;
                }
                html.push('<div class="markdown-table-wrap"><table><thead><tr>' + headers.map(function (cell) {
                    return '<th>' + renderInline(cell) + '</th>';
                }).join('') + '</tr></thead><tbody>' + rows.map(function (row) {
                    return '<tr>' + headers.map(function (_, cellIndex) {
                        return '<td>' + renderInline(row[cellIndex] || '') + '</td>';
                    }).join('') + '</tr>';
                }).join('') + '</tbody></table></div>');
                continue;
            }

            if (/^\s*>\s?/.test(line)) {
                var quote = [];
                while (index < lines.length && /^\s*>\s?/.test(lines[index])) {
                    quote.push(lines[index].replace(/^\s*>\s?/, ''));
                    index += 1;
                }
                html.push('<blockquote>' + renderMarkdown(quote.join('\n')) + '</blockquote>');
                continue;
            }

            var listMatch = line.match(/^\s*(?:([-+*])|(\d+)\.)\s+(.+)$/);
            if (listMatch) {
                var ordered = !!listMatch[2];
                var listTag = ordered ? 'ol' : 'ul';
                var items = [];
                while (index < lines.length) {
                    var item = lines[index].match(/^\s*(?:([-+*])|(\d+)\.)\s+(.+)$/);
                    if (!item || (!!item[2]) !== ordered) break;
                    items.push('<li>' + renderInline(item[3]) + '</li>');
                    index += 1;
                }
                html.push('<' + listTag + '>' + items.join('') + '</' + listTag + '>');
                continue;
            }

            var paragraph = [line.trim()];
            index += 1;
            while (index < lines.length && lines[index].trim()) {
                var next = lines[index];
                if (/^\s*```/.test(next) || /^\s{0,3}#{1,6}\s+/.test(next) ||
                    /^\s*>\s?/.test(next) || /^\s*(?:[-+*]|\d+\.)\s+/.test(next) ||
                    (next.indexOf('|') !== -1 && index + 1 < lines.length && isTableDivider(lines[index + 1]))) break;
                paragraph.push(next.trim());
                index += 1;
            }
            html.push('<p>' + paragraph.map(renderInline).join('<br>') + '</p>');
        }
        return html.join('');
    }

    function sanitizeHtml(html) {
        if (typeof document === 'undefined') return escapeHtml(html);
        var template = document.createElement('template');
        template.innerHTML = String(html == null ? '' : html);

        Array.prototype.slice.call(template.content.querySelectorAll('*')).forEach(function (node) {
            if (!allowedTags[node.tagName]) {
                node.replaceWith(document.createTextNode(node.textContent || ''));
                return;
            }
            Array.prototype.slice.call(node.attributes).forEach(function (attribute) {
                var name = attribute.name.toLowerCase();
                var keep = false;
                if (node.tagName === 'A' && ['href', 'title', 'target', 'rel'].indexOf(name) !== -1) keep = true;
                if (node.tagName === 'IMG' && ['src', 'alt', 'title', 'width', 'height'].indexOf(name) !== -1) keep = true;
                if (name === 'class' && /^(table|table-bordered|language-[A-Za-z0-9_+-]+)$/.test(attribute.value)) keep = true;
                if (!keep) node.removeAttribute(attribute.name);
            });

            if (node.tagName === 'A') {
                var href = safeUrl(node.getAttribute('href'), false);
                if (href) node.setAttribute('href', href); else node.removeAttribute('href');
                node.setAttribute('rel', 'noopener noreferrer');
                if (node.getAttribute('target') === '_blank') node.setAttribute('target', '_blank');
                else node.removeAttribute('target');
            }
            if (node.tagName === 'IMG') {
                var src = safeUrl(node.getAttribute('src'), true);
                if (src) {
                    node.setAttribute('src', src);
                    node.setAttribute('loading', 'lazy');
                } else {
                    node.remove();
                }
            }
        });
        return template.innerHTML;
    }

    function plainText(html) {
        if (typeof document === 'undefined') return String(html || '').replace(/<[^>]*>/g, ' ');
        var container = document.createElement('div');
        container.innerHTML = sanitizeHtml(html);
        return (container.textContent || '').replace(/\s+/g, ' ').trim();
    }

    function renderContent(content, format) {
        return String(format || '').toLowerCase() === 'markdown'
            ? renderMarkdown(content)
            : sanitizeHtml(content);
    }

    global.PgsContentRenderer = {
        escapeHtml: escapeHtml,
        plainText: plainText,
        renderContent: renderContent,
        renderMarkdown: renderMarkdown,
        sanitizeHtml: sanitizeHtml
    };
})(window);
