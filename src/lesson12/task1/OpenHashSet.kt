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
    val size: Int get() = elements.count({ it != null })

    /**
     * Признак пустоты
     */
    fun isEmpty(): Boolean = size == 0

    private fun hash(element: T) = element.hashCode() % capacity

    /**
     * Добавление элемента.
     * Вернуть true, если элемент был успешно добавлен,
     * или false, если такой элемент уже был в таблице, или превышена вместимость таблицы.
     */
    fun add(element: T): Boolean {
        if (element == null) throw IllegalArgumentException("This HashSet impl can not store null values")

        val hash = hash(element)

        if (size != capacity) {
            var i = 0
            while(i < capacity) {
                val index = (hash + i) % capacity
                when (elements[index]) {
                    null -> {
                        elements[index] = element
                        return true
                    }
                    element -> return false
                }
                i += 1
            }
        }
        return false
    }

    /**
     * Проверка, входит ли заданный элемент в хеш-таблицу
     */
    operator fun contains(element: T): Boolean {
        val hash = hash(element)

        var i = 0
        while(i < capacity) {
            val index = (hash + i) % capacity
            if (elements[index] == element) return true
            else if (elements[index] == null) return false
            i += 1
        }
        return false
    }

    /**
     * Таблицы равны, если в них одинаковое количество элементов,
     * и любой элемент из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean =
        if (this === other) true
        else if (other !is OpenHashSet<*>) false
        else if (other.size == 0 && size == 0) true
        else if (other.size != size) false
        // Horrible hack for horrible language
        else if (elements.find({ it != null })!!::class != other.elements.find({ it != null })!!::class) false
        else {
            for (i in elements) {
                if (i != null && (other as OpenHashSet<T>).contains(i as T) == false) false
            }
            true
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