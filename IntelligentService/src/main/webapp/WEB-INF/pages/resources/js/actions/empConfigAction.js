export function handleEmpConfigAddClick(filter) {
    return {
        type: 'EMPCONFIG_ADD'
    }
}

export function handleEmpConfigSetDialogShow(visible, id) {
    return {
        type: 'SET_EMPCONFIG_DIALOG_VISIBLE',
        visible: visible,
        id: id
    }
}

export function handleEmpConfigDelete(id) {
    return {
        type: 'SET_EMPCONFIG_DELETE',
        id: id
    }
}