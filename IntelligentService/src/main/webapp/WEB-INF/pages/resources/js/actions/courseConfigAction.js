export function handleCourseConfigTabClick(filter) {
    return {
        type: 'SET_COURSECONFIG_FILTER',
        filter: filter
    }
}

export function handleCourseConfigDelete(id) {
    return {
        type: 'SET_COURSECONFIG_DELETE',
        id
    }
}

export function handleCourseConfigSetDialogShow(visible, id) {
    return {
        type: 'SET_COURSECONFIG_DIALOG_VISIBLE',
        visible: visible,
        id: id
    }
}