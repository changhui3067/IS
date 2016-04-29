export function handleHotjobsTabClick(filter) {
    return {
        type: 'SET_HOTJOBS_FILTER',
        filter: filter
    }
}

export function handleHotjobsSetDialogShow(visible) {
    return {
        type: 'SET_HOTJOBS_DIALOG_VISIBLE',
        visible: visible
    }
}