
const handleMenuClick = (menuItem) => {
    console.log("action: handleMenuClick");
    return {
        type: 'SET_MENU_TEXT',
        menuItem: menuItem
    }
}

const handleTileClick = (tileItem) => {
    console.log("action: handleItemClick");
    return {
        type: 'SET_MENU_TEXT',
        menuItem: menuItem
    }
}

export default {handleMenuClick, handleTileClick}