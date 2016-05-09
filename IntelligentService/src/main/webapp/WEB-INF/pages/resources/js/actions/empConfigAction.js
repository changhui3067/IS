export const EMPCONFIG_ADD = 'EMPCONFIG_ADD'
export const SET_EMPCONFIG_DIALOG_VISIBLE = 'SET_EMPCONFIG_DIALOG_VISIBLE'
export const SET_EMPCONFIG_DELETE = 'SET_EMPCONFIG_DELETE'

export function handleEmpConfigAddClick(filter) {
    return {
        type: EMPCONFIG_ADD
    }
}

export function handleEmpConfigSetDialogShow(visible, id) {
    return {
        type: SET_EMPCONFIG_DIALOG_VISIBLE,
        visible: visible,
        id: id
    }
}

export function handleEmpConfigDelete(id) {
    return {
        type: SET_EMPCONFIG_DELETE,
        id: id
    }
}