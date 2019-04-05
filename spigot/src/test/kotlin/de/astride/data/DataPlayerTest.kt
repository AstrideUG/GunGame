package de.astride.data

import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 00:47.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class DataPlayerTest {

    //given
    companion object {
        const val expectedJson: String =
            """{"damageable":${DamageableTest.expectedJson},"dataExp":${DataExpTest.expectedJson},"dataFood":${DataFoodTest.expectedJson},"dataHealth":${DataHealthTest.expectedJson},"dataLocation":${DataLocationTest.expectedJson},"fireable":${FireableTest.expectedJson},"flyable":${FlyableTest.expectedJson},"inventories":${InventoriesTest.expectedJson},"metaData":${MetaDataTest.expectedJson},"names":${NamesTest.expectedJson},"otherData":${OtherDataTest.expectedJson},"permissables":${PermissablesTest.expectedJson},"sleepable":${SleepableTest.expectedJson},"speeds":${SpeedsTest.expectedJson},"targetable":${TargetableTest.expectedJson},"whiteAndBlackList":${DataLocationTest.expectedJson}"""
        val expectedSource: DataPlayer = DataPlayer(
            DamageableTest.expectedSource,
            DataExpTest.expectedSource,
            DataFoodTest.expectedSource,
            DataHealthTest.expectedSource,
            DataLocationTest.expectedSource,
            FireableTest.expectedSource,
            FlyableTest.expectedSource,
            InventoriesTest.expectedSource,
            MetaDataTest.expectedSource,
            NamesTest.expectedSource,
            OtherDataTest.expectedSource,
            PermissablesTest.expectedSource,
            SleepableTest.expectedSource,
            SpeedsTest.expectedSource,
            TargetableTest.expectedSource,
            WhiteAndBlackListTest.expectedSource
        )
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}