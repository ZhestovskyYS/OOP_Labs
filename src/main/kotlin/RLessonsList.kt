
import data.Lesson
import data.lessonsList
import org.w3c.dom.events.Event
import react.*
import react.dom.h2

interface RLessonsListProps : RProps {
    var lessons: Lesson/*ArrayList<Lesson>*/

}

interface RLessonsListState : RState{
    var present: Array<Boolean>
}

class RLessonsList : RComponent<RLessonsListProps, RLessonsListState>(){
    override fun componentWillMount() {
        state.apply {
            present = Array(props.lessons.students.size){false}
        }
    }

    override fun RBuilder.render() {
        lessonsList.let {
            h2 {
                +it.subject
                studentList(it.students.toTypedArray(), state.present, onIndex())
            }
        }
    }

    fun RBuilder.onIndex(): (Int) -> (Event) -> Unit = {
        onClick(it)
    }

    fun RBuilder.onClick(index: Int):  (Event) -> Unit = {
        setState {
            present[index] = !present[index]
        }
    }
}

fun RBuilder.lessonsList(lessons: Lesson/* ArrayList<Lesson>*/) =
    child(RLessonsList::class){
        attrs.lessons = lessons
    }