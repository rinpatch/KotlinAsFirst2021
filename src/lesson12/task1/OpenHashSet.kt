@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "хеш-таблица с открытой адресацией"
 *
 * Общая сложность задания -- сложная, общая ценность в баллах -- 20.
 * Объект класса хранит данные типа T в виде хеш-таблицы.
 * Хеш-таблица не может содержать равные по equals элементы.
 * Подробности по организации см. статью википедии "Хеш-таблица", раздел "Открытая адресация".
 * Методы: добавление элемента, проверка вхождения элемента, сравнение двух таблиц на равенство.
 * В этом задании не разрешается использовать библиотечные классы HashSet, HashMap и им подобные,
 * а также любые функции, создающие множества (mutableSetOf и пр.).
 *
 * В конструктор хеш-таблицы передаётся её вместимость (максимальное количество элементов)
 */
class OpenHashSet<T>(val capacity: Int) {

    /**
     * Массив для хранения элементов хеш-таблицы
     */
    internal val elements = Array<Any?>(capacity) { null }

    /**
     * Число элементов в хеш-таблице
     */
    private var _size: Int = 0
    val size: Int get() = _size

    /**
     * Признак пустоты
     */
    fun isEmpty(): Boolean = size == 0

    private fun hash(element: T) = element.hashCode() % capacity

    private fun linearProbeIndex(element: T): Int {
        val hash = hash(element)
        var i = 0
        while (i < capacity) {
            val index = (hash + i) % capacity
            when (elements[index]) {
                element -> return index
                null -> return index
            }
            i += 1
        }
        return -1
    }

    /**
     * Добавление элемента.
     * Вернуть true, если элемент был успешно добавлен,
     * или false, если такой элемент уже был в таблице, или превышена вместимость таблицы.
     */
    fun add(element: T): Boolean {
        if (element == null) throw IllegalArgumentException("This HashSet impl can not store null values")

        if (size != capacity) {
            return when (val index = linearProbeIndex(element)) {
                -1 -> false
                else -> {
                    if (elements[index] == null) {
                        elements[index] = element
                        _size++
                        true
                    } else false
                }
            }
        }
        return false
    }

    /**
     * Проверка, входит ли заданный элемент в хеш-таблицу
     */
    operator fun contains(element: T): Boolean =
        when (val index = linearProbeIndex(element)) {
            -1 -> false
            else -> elements[index] != null
        }

    /**
     * Таблицы равны, если в них одинаковое количество элементов,
     * и любой элемент из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean {
        return if (this === other) true
        else if (other !is OpenHashSet<*>) false
        else if (other.size == 0 && size == 0) true
        else if (other.size != size) false
        else {
            for (i in other.elements) {
                if (i != null && !contains(i as T)) return false
            }
            true
        }
    }

    override fun hashCode(): Int {
        val hashes = elements.filter({ it != null }).map { it.hashCode() }.sorted()
        var hashCode = 0
        for (hash in hashes) {
            hashCode = 31 * hashCode + hash
        }
        return hashCode
    }
}