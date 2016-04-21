
export const handleMenuClick = (menuItem) => {
    console.log("action: handleMenuClick");
    return {
        type: 'SET_MENU_TEXT',
        menuItem: menuItem
    }
}