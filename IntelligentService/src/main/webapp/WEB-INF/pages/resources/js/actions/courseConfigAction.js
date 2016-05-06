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