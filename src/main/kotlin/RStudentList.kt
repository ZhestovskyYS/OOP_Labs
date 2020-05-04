import data.Student
import org.w3c.dom.events.Event
import react.*
import react.dom.li
import react.dom.ol
import react.dom.span

interface RStudentListProps : RProps {
    var students: Array<Student>
    var present: Array<Boolean>
    var onClick:  (Int) -> (Event)->Unit
}

val RStudentList =
    functionalComponent<RStudentListProps>{
         span {
                it.students.mapIndexed {index , student ->
                    li {
                        rstudent(student, it.present[index], it.onClick(index))
                    }
                }
         }
    }

fun RBuilder.studentList(
    students: Array<Student>,
    present: Array<Boolean>,
    onClick:  (Int) -> (Event)->Unit
) =
    child(RStudentList) {
        attrs.students = students
        attrs.present = present
        attrs.onClick = onClick
    }
