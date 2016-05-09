export const SET_JOBCONFIG_FILTER = 'SET_JOBCONFIG_FILTER'
export const SET_JOBCONFIG_DELETE = 'SET_JOBCONFIG_DELETE'
export const SET_JOBCONFIG_CLOSE = 'SET_JOBCONFIG_CLOSE'
export const SET_JOBCONFIG_DIALOG_VISIBLE = 'SET_JOBCONFIG_DIALOG_VISIBLE'

export function handleJobConfigTabClick(filter) {
    return {
        type: SET_JOBCONFIG_FILTER,
        filter: filter
    }
}

export function handleJobConfigDelete(id) {
    return {
        type: SET_JOBCONFIG_DELETE,
        id
    }
}

export function handleJobConfigClose(id) {
    return {
        type: SET_JOBCONFIG_CLOSE,
        id
    }
}

export function handleJobConfigDialogShow(visible, id) {
    return {
        type: SET_JOBCONFIG_DIALOG_VISIBLE,
        id: id,
        visible: visible
    }
}