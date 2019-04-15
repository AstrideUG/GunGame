package de.astride.ffa.functions

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.04.2019 04:08.
 * Current Version: 1.0 (06.04.2019 - 06.04.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.04.2019 03:06.
 * Current Version: 1.0 (06.04.2019 - 06.04.2019)
 */
@JvmName("replaceNullable")
fun String?.replace(key: String, value: Any): String? = this?.replace(key, value)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.04.2019 06:24.
 * Current Version: 1.0 (06.04.2019 - 06.04.2019)
 */
fun String.replace(key: String, value: Any): String = replace("@$key@", value.toString(), ignoreCase = true)