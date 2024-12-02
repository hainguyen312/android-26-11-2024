package vn.edu.hust.studentman

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class StudentListFragment : Fragment() {
    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )
    private lateinit var studentAdapter: StudentAdapter
    private var selectedStudentIndex: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.layout_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<ListView>(R.id.listView_students)
        val fabAddStudent = view.findViewById<View>(R.id.fab_add_student)

        // Thiết lập adapter cho ListView
        studentAdapter = StudentAdapter(requireContext(), students)
        listView.adapter = studentAdapter

        registerForContextMenu(listView)

        // Lắng nghe sự kiện nhấn nút thêm
        fabAddStudent.setOnClickListener {
            findNavController().navigate(R.id.action_addStudent)
        }

        // Lắng nghe dữ liệu trả về từ AddStudentFragment
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<StudentModel>("newStudent")
            ?.observe(viewLifecycleOwner) { newStudent ->
                students.add(newStudent)
                studentAdapter.notifyDataSetChanged()
            }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add_new) {
            findNavController().navigate(R.id.action_addStudent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            R.id.menu_edit -> {
                val bundle = Bundle().apply {
                    putSerializable("student", students[info.position])
                }
                findNavController().navigate(R.id.action_editStudent, bundle)
            }
            R.id.menu_remove -> {
                students.removeAt(info.position)
                studentAdapter.notifyDataSetChanged()
            }
        }
        return super.onContextItemSelected(item)
    }
}
