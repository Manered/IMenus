package dev.manere.imenus.item;

import dev.manere.imenus.menu.Menu;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for providing page items in a menu context.
 */
@FunctionalInterface
public interface PageItemProvider {
    /**
     * Creates a {@link PageItemProvider} instance from an existing provider.
     *
     * @param provider the existing provider.
     * @return a {@link PageItemProvider} instance.
     */
    @NotNull
    static PageItemProvider of(final @NotNull PageItemProvider provider) {
        return provider;
    }

    /**
     * Retrieves the {@link PageItemData} for a specific context.
     *
     * @param context the context for which the page item is retrieved.
     * @return the {@link PageItemData} associated with the context.
     */
    @NotNull
    PageItemData item(final @NotNull Context context);

    /**
     * Creates a context for the specified {@link Menu}.
     *
     * @param menu the menu for which the context is created.
     * @return a {@link Context} instance for the menu.
     */
    @NotNull
    static Context context(final @NotNull Menu menu) {
        return context(menu.getPages(), menu.getPage());
    }

    /**
     * Creates a context with specified pages and current page.
     *
     * @param pages the total number of pages.
     * @param page  the current page.
     * @return a {@link Context} instance with the provided pages and current page.
     */
    @NotNull
    static Context context(final int pages, final int page) {
        return new Context(pages, page);
    }

    /**
     * Represents the context of a menu, including total pages and current page.
     */
    record Context(int pages, int page) {
        /**
         * Retrieves the next page number.
         *
         * @return the next page number.
         */
        public int next() {
            return page + 1;
        }

        /**
         * Retrieves the previous page number.
         *
         * @return the previous page number.
         */
        public int previous() {
            return page - 1;
        }

        /**
         * Retrieves the current page number.
         *
         * @return the current page number.
         */
        public int current() {
            return page;
        }
    }
}
