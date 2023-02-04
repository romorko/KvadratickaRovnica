import java.security.InvalidParameterException
import kotlin.math.sqrt
import kotlin.system.exitProcess

class RovnicaKv(pa: Int, pb: Int, pc: Int)
{
    private var a: Int = pa
        set(value)
        {
            field = if (value != 0) value else throw InvalidParameterException("Konstanta pri x^2 nesmie byt 0!")
        }

    init
    {
        if (a == 0)
        {
            print("Konstanta pri x^2 nesmie byt 0!\nRovnica nie je kvadraticka!")
            exitProcess(1)
            //throw InvalidParameterException("Konstanta pri x^2 nesmie byt 0! Rovnica nie je kvadraticka!")
        }
    }

    private var b: Int = pb
    private var c: Int = pc

    //sekundarne konstruktory
    constructor(text: String) : this(1, 0, 0)
    {
        overVstup(text)
    }

    constructor() : this(1, 0, 0)
    {
        print("Zadaj rovnicu v tvare: ax^2 + bx + c = 0  alebo trojicu koeficientov v tvare: a b c:")
        val vstup = readln()
        overVstup(vstup)
    }

    //konstanty pre vyctovy typ
    enum class PocetKorenov(val kolko: Int)
    {
        ZIADNY(0),
        JEDEN(1),
        DVA(2)
    }

    //vnorena trieda pre korene
    inner class Korene(private val pocet: PocetKorenov, private val koren1: Float = 0f, private val koren2: Float = 0f)
    {
        override fun toString(): String
        {
            return when (pocet)
            {
                PocetKorenov.ZIADNY -> " nema ziadny koren!"
                PocetKorenov.JEDEN  -> String.format(" ma jeden koren x = %.2f", koren1)
                PocetKorenov.DVA    -> String.format(" ma dva korene: x1 = %.2f , x2 = %.2f", koren1, koren2)
            }
        }
    }

    //overenie spravnosti vstupu pomocou regularneho vyrazu
    private fun overVstup(zadanie: String)
    {
        val kontrola =
            """\s*(?<koefA>-?[1-9]d*)x\^2\s*(?<koefB>[+-]\d+)x\s*(?<koefC>[+-]\d+)\s*=\s*0""".toRegex(setOf(RegexOption.IGNORE_CASE))
        val kontrola1 =
            """\s*(?<koefA>-?[1-9]*)\s*(?<koefB>-?\d+)\s*(?<koefC>-?\d+)\s*""".toRegex(setOf(RegexOption.IGNORE_CASE))
        var kontroluj = zadanie
        while (true)
        {
            try
            {
                val dobraRovnica =
                                    if (kontrola.matches(kontroluj))
                                    {
                                        kontrola.find(kontroluj)
                                    }
                                    else if (kontrola1.matches(kontroluj))
                                    {
                                        kontrola1.find(kontroluj)
                                    }
                                    else
                                    {
                                        throw InvalidParameterException("Rovnica bola zadana v nespravnom tvare!")
                                    }
                a = dobraRovnica!!.groups["koefA"]!!.value.toInt()
                b = dobraRovnica.groups["koefB"]!!.value.toInt()
                c = dobraRovnica.groups["koefC"]!!.value.toInt()
                break
            }
            catch (e: InvalidParameterException)
            {
                print(e.message + "Zadaj rovnicu v tvare: ax^2 + bx + c = 0: ")
                kontroluj = readln()
            }
        }
    }

    fun dajKorene(): Korene
    {
        val D = b * b - 4 * a * c
        return when
        {
            D < 0  -> Korene(PocetKorenov.ZIADNY)
            D == 0 -> Korene(PocetKorenov.JEDEN, (-b + sqrt(D.toFloat())) / (2 * a))
            else   -> Korene(PocetKorenov.DVA, (-b + sqrt(D.toFloat())) / (2 * a), (-b - sqrt(D.toFloat())) / (2 * a))
        }
    }

    override fun toString(): String
    {
        return "Rovnica: ${if (a != 1) a else ""}x^2 " + (if (b >= 0) "+ ${b}x" else "${b}x") + (if (c >= 0) " + $c = 0" else " $c = 0")
    }
}