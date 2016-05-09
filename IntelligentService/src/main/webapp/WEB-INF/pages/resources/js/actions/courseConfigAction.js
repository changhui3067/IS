export const SET_COURSECONFIG_FILTER = 'SET_COURSECONFIG_FILTER'
export const SET_COURSECONFIG_DELETE = 'SET_COURSECONFIG_DELETE'
export const SET_COURSECONFIG_DIALOG_VISIBLE = 'SET_COURSECONFIG_DIALOG_VISIBLE'

export function handleCourseConfigTabClick(filter) {
    return {
        type: SET_COURSECONFIG_FILTER,
        filter: filter
    }
}

export function handleCourseConfigDelete(id) {
    return {
        type: SET_COURSECONFIG_DELETE,
        id
    }
}

export function handleCourseConfigSetDialogShow(visible, id) {
    return {
        type: SET_COURSECONFIG_DIALOG_VISIBLE,
        visible: visible,
        id: id
    }
}