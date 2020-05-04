package component

import data.*
import hoc.withDisplayName
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import kotlin.browser.document
import kotlin.reflect.KClass

interface AppProps : RProps


interface AppState : RState {
    var lessons: Array<Lesson>
    var students: Array<Student>
    var presents: Array<Array<Boolean>>
}

interface RouteNumberResult : RProps {
    var number: String
}

class App : RComponent<AppProps, AppState>() {
    override fun componentWillMount() {
        state.students = studentList
        state.lessons = lessonsList
        state.presents = Array(state.lessons.size) {
            Array(state.students.size) { false }
        }

    }

    override fun RBuilder.render() {
        header {
            h1 { +"App" }
            nav {
                ul {
                    li { navLink("/lessons") { +"Lessons" } }
                    li { navLink("/students") { +"Students" } }
                    li { navLink("/redactStudents") { +"Redact Students" } }
                    li { navLink("/redactLessons") { +"Redact Lessons" } }
                }
            }
        }

        switch {
            route("/lessons",
                exact = true,
                render = {
                    anyList(state.lessons, "Lessons", "/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                    anyList(state.students, "Students", "/students")
                }
            )
            route("/lessons/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val lesson = state.lessons.getOrNull(num)
                    if (lesson != null)
                        anyFull(
                            RBuilder::student,
                            lesson,
                            state.students,
                            state.presents[num]
                        ) { onClick(num, it) }
                    else
                        p { +"No such lesson" }
                }
            )
            route("/students/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val student = state.students.getOrNull(num)
                    if (student != null)
                        anyFull(
                            RBuilder::lesson,
                            student,
                            state.lessons,
                            state.presents.map {
                                it[num]
                            }.toTypedArray()
                        ) { onClick(it, num) }
                    else
                        p { +"No such student" }
                }
            )
            route( "/redactStudents",
                render = {
                    redactList(
                        RBuilder::anyList,
                        RBuilder::redactStudent,
                        state.students,
                        addStudent(),
                        deleteStudent(),
                        "Student",
                        "/students"
                    )
                }
            )
            route("/redactLessons",
                render ={
                    redactList(
                        RBuilder::anyList,
                        RBuilder::redactLesson,
                        state.lessons,
                        addLesson(),
                        deleteLesson(),
                        "Lesson",
                        "/lessons"
                    )
                }
            )
        }
    }



    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]
            }
        }

    fun addStudent(): (Event) -> Unit = {
        val fullName = document.getElementById("Student") as HTMLInputElement
        val newStudent = fullName.value.split(" ")
        setState{
            students += Student(newStudent[0], newStudent[1])
            presents += arrayOf(Array(state.students.size){false})
        }
    }

    fun deleteStudent(): (Event) -> Unit ={
        val fullName = document.getElementById("Student") as HTMLInputElement
        var redactedPresents = state.presents
        val redactedStudents = state.students.toMutableList().apply{
            for (i in state.students.indices)
                if(fullName.value.split(" ")[0]==state.students[i].firstname &&
                fullName.value.split(" ")[1]==state.students[i].surname){
                removeAt(i)
                redactedPresents = state.presents.toMutableList().apply{
                    removeAt(i)
                }.toTypedArray()
            }
        }.toTypedArray()
        setState{
            students = redactedStudents
            presents = redactedPresents
        }
    }

    fun addLesson(): (Event) -> Unit = {
        val lessonName = document.getElementById("Lesson") as HTMLInputElement
        setState{
            lessons+=Lesson(lessonName.value)
            presents+= arrayOf(Array(state.students.size){false})
        }
    }

    fun deleteLesson(): (Event) -> Unit = {
        val lessonName = document.getElementById("Lesson") as HTMLInputElement
        var redactedPresents = state.presents
        val redactedLessons = state.lessons.toMutableList().apply {
            for (i in state.lessons.indices)
                if(lessonName.value== state.lessons[i].name){
                    removeAt(i)
                    redactedPresents = state.presents.toMutableList().apply{
                        removeAt(i)
                    }
                        .toTypedArray()
                }
            }
            .toTypedArray()
        setState{
            lessons = redactedLessons
            presents = redactedPresents
        }
    }
}

fun RBuilder.app() =  child(withDisplayName("AppHoc", App::class)) {
    }





