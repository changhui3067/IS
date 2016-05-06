export function handleCoursesTabClick(filter) {
    return {
        type: 'SET_COURSES_FILTER',
        filter: filter
    }
}

export function handleCoursesFinishedClick(id) {
    return {
        type: 'SET_COURSE_DELETE',
        id: id
    }
}

