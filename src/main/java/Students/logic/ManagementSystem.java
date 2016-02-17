package Students.logic;

        import java.io.FileNotFoundException;
        import java.io.PrintStream;
        import java.io.UnsupportedEncodingException;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Collection;
        import java.util.Iterator;
        import java.util.List;
        import java.util.TreeSet;

public class ManagementSystem {

    private List<Group> groups;
    private Collection<Student> students;

    private static ManagementSystem instance;

    private ManagementSystem() {
        loadGroups();
        loadStudents();
    }

    public static synchronized ManagementSystem getInstance() {
        if (instance == null) {
            instance = new ManagementSystem();
        }
        return instance;
    }

    public static void main(String[] args) {
        // Вывод в текстовый файл из-за проблем кодировки, потом разобрался
        try {
            System.setOut(new PrintStream("epam.txt"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return;
        }

        ManagementSystem ms = ManagementSystem.getInstance();

        // Просмотр полного списка групп
        printString("Полный список групп");
        printString("*******************");
        List<Group> allGroups = ms.getGroups();
        for (Group gi : allGroups) {
            printString(gi);
        }
        printString();

        // Просмотр полного списка студентов
        printString("Полный список студентов");
        printString("***********************");
        Collection<Student> allStudends = ms.getAllStudents();
        for (Student si : allStudends) {
            printString(si);
        }
        printString();

        // Вывод списков студентов по группам
        printString("Список студентов по группам");
        printString("***************************");
        List<Group> groups = ms.getGroups();
        // Проверяем все группы
        for (Group gi : groups) {
            printString("---> Группа:" + gi.getNameGroup());
            // Получаем список студентов для конкретной группы
            Collection<Student> students = ms.getStudentsFromGroup(gi, 2015);
            for (Student si : students) {
                printString(si);
            }
        }
        printString();

        // Создание нового студента и добавление его к списку
        Student s = new Student();
        s.setStudentId(5);
        s.setFirstName("Нургазин");
        s.setPatronymic("Канатович");
        s.setSurName("Марлен");
        s.setSex('М');
        Calendar c = Calendar.getInstance();
        c.set(1991, 8, 26);
        s.setDateOfBirth(c.getTime());
        s.setGroupId(1);
        s.setEducationYear(2015);
        printString("Добавление студента:" + s);
        printString("********************");
        ms.insertStudent(s);
        printString("--->> Полный список студентов после добавления");
        allStudends = ms.getAllStudents();
        for (Student si : allStudends) {
            printString(si);
        }
        printString();

        // Изменим данные о студенте - Нургазина станет у нас Увалиевым
        s = new Student();
        s.setStudentId(5);
        s.setFirstName("Увалиев");
        s.setPatronymic("Канатович");
        s.setSurName("Марлен");
        s.setSex('М');
        c = Calendar.getInstance();
        c.set(1991, 8, 26);
        s.setDateOfBirth(c.getTime());
        s.setGroupId(1);
        s.setEducationYear(2015);
        printString("Редактирование данных студента:" + s);
        printString("*******************************");
        ms.updateStudent(s);
        printString("--->> Полный список студентов после редактирования");
        allStudends = ms.getAllStudents();
        for (Student si : allStudends) {
            printString(si);
        }
        printString();

        // Удалим нашего студента
        printString("Удаление студента:" + s);
        printString("******************");
        ms.deleteStudent(s);
        printString("--->> Полный список студентов после удаления");
        allStudends = ms.getAllStudents();
        for (Student si : allStudends) {
            printString(si);
        }
        printString();

        // Здесь мы переводим всех студентов одной группы в другую
        Group g1 = groups.get(0);
        Group g2 = groups.get(1);
        printString("Перевод студентов из 1-ой во 2-ю группу");
        printString("***************************************");
        ms.moveStudentsToGroup(g1, 2015, g2, 2016);
        printString("--->> Полный список студентов после перевода");
        allStudends = ms.getAllStudents();
        for (Student si : allStudends) {
            printString(si);
        }
        printString();

        // Удаляем студентов из группы
        printString("Удаление студентов из группы:" + g2 + " в 2015 году");
        printString("*****************************");
        ms.removeStudentsFromGroup(g2, 2015);
        printString("--->> Полный список студентов после удаления");
        allStudends = ms.getAllStudents();
        for (Iterator i = allStudends.iterator(); i.hasNext();) {
            printString(i.next());
        }
        printString();
    }

    // Метод создает две группы и помещает их в коллекцию для групп
    public void loadGroups() {
        // Проверяем - может быть наш список еще не создан вообще
        if (groups == null) {
            groups = new ArrayList<Group>();
        } else {
            groups.clear();
        }
        Group g = null;

        g = new Group();
        g.setGroupId(1);
        g.setNameGroup("Первая");
        g.setCurator("Терлецкая");
        g.setSpeciality("Информационные системы");
        groups.add(g);

        g = new Group();
        g.setGroupId(2);
        g.setNameGroup("Вторая");
        g.setCurator("Эттель");
        g.setSpeciality("Информатика");
        groups.add(g);
    }

    // Метод создает несколько студентов и помещает их в коллекцию
    public void loadStudents() {
        if (students == null) {
            // Мы используем коллекцию, которая автоматически сортирует свои элементы
            students = new TreeSet<Student>();
        } else {
            students.clear();
        }

        Student s = null;
        Calendar c = Calendar.getInstance();

        // Вторая группа
        s = new Student();
        s.setStudentId(1);
        s.setFirstName("Рашид");
        s.setPatronymic("Хамзатович");
        s.setSurName("Рахимов");
        s.setSex('М');
        c.set(1992, 3, 20);
        s.setDateOfBirth(c.getTime());
        s.setGroupId(2);
        s.setEducationYear(2015);
        students.add(s);

        s = new Student();
        s.setStudentId(2);
        s.setFirstName("Наталья");
        s.setPatronymic("Владимировна");
        s.setSurName("Чернова");
        s.setSex('Ж');
        c.set(1992, 6, 10);
        s.setDateOfBirth(c.getTime());
        s.setGroupId(2);
        s.setEducationYear(2015);
        students.add(s);

        // Первая группа
        s = new Student();
        s.setStudentId(3);
        s.setFirstName("Владимир");
        s.setPatronymic("Викторович");
        s.setSurName("Белоусов");
        s.setSex('М');
        c.set(1991, 3, 12);
        s.setDateOfBirth(c.getTime());
        s.setEducationYear(2015);
        s.setGroupId(1);
        students.add(s);

        s = new Student();
        s.setStudentId(4);
        s.setFirstName("Юлия");
        s.setPatronymic("Сергеевна");
        s.setSurName("Хрусталева");
        s.setSex('Ж');
        c.set(1991, 7, 19);
        s.setDateOfBirth(c.getTime());
        s.setEducationYear(2015);
        s.setGroupId(1);
        students.add(s);
    }

    // Получить список групп
    public List<Group> getGroups() {
        return groups;
    }

    // Получить список всех студентов
    public Collection<Student> getAllStudents() {
        return students;
    }

    // Получить список студентов для определенной группы
    public Collection<Student> getStudentsFromGroup(Group group, int year) {
        Collection<Student> l = new TreeSet<Student>();
        for (Student si : students) {
            if (si.getGroupId() == group.getGroupId() && si.getEducationYear() == year) {
                l.add(si);
            }
        }
        return l;
    }

    // Перевести студентов из одной группы с одним годом обучения в другую группу с другим годом обучения
    public void moveStudentsToGroup(Group oldGroup, int oldYear, Group newGroup, int newYear) {
        for (Student si : students) {
            if (si.getGroupId() == oldGroup.getGroupId() && si.getEducationYear() == oldYear) {
                si.setGroupId(newGroup.getGroupId());
                si.setEducationYear(newYear);
            }
        }
    }

    // Удалить всех студентов из определенной группы
    public void removeStudentsFromGroup(Group group, int year) {
        // Мы создадим новый список студентов БЕЗ тех, кого мы хотим удалить.
          Collection<Student> tmp = new TreeSet<Student>();
        for (Student si : students) {
            if (si.getGroupId() != group.getGroupId() || si.getEducationYear() != year) {
                tmp.add(si);
            }
        }
        students = tmp;
    }

    // Добавить студента
    public void insertStudent(Student student) {
        // Просто добавляем объект в коллекцию
        students.add(student);
    }

    // Обновить данные о студенте
    public void updateStudent(Student student) {
        // Надо найти нужного студента (по его ИД) и заменить поля
        Student updStudent = null;
        for (Student si : students) {
            if (si.getStudentId() == student.getStudentId()) {
                // Вот этот студент - запоминаем его и прекращаем цикл
                updStudent = si;
                break;
            }
        }
        updStudent.setFirstName(student.getFirstName());
        updStudent.setPatronymic(student.getPatronymic());
        updStudent.setSurName(student.getSurName());
        updStudent.setSex(student.getSex());
        updStudent.setDateOfBirth(student.getDateOfBirth());
        updStudent.setGroupId(student.getGroupId());
        updStudent.setEducationYear(student.getEducationYear());
    }

    // Удалить студента
    public void deleteStudent(Student student) {
        // Надо найти нужного студента (по его ИД) и удалить
        Student delStudent = null;
        for (Student si : students) {
            if (si.getStudentId() == student.getStudentId()) {
                // Вот этот студент - запоминаем его и прекращаем цикл
                delStudent = si;
                break;
            }
        }
        students.remove(delStudent);
    }

   //Проблемы с кодировкой
    public static void printString(Object s) {
        //System.out.println(s.toString());
        try {
            System.out.println(new String(s.toString().getBytes("UTF-8"), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public static void printString() {
        System.out.println();
    }
}