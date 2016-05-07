export function handleJobConfigTabClick(filter) {
    return {
        type: 'SET_JOBCONFIG_FILTER',
        filter: filter
    }
}

export function handleJobConfigDelete(id) {
    return {
        type: 'SET_JOBCONFIG_DELETE',
        id
    }
}

export function handleJobConfigClose(id) {
    return {
        type: 'SET_JOBCONFIG_CLOSE',
        id
    }
}

export function handleJobConfigDialogShow(visible, id) {
    return {
        type: 'SET_JOBCONFIG_DIALOG_VISIBLE',
        id: id,
        visible: visible
    }
}