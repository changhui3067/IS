
export function handleMenuClick (menuItem) {
    console.log("action: handleMenuClick");
    return {
        type: 'SET_MENU_TEXT',
        menuItem: menuItem
    }
}

export function handleTileClick (tileItem) {
    console.log("action: handleItemClick");
    return {
        type: 'SET_MENU_TEXT',
        menuItem: tileItem
    }
}

