package com.basemodule.viewmodel

import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BaseViewModelTest{
    private lateinit var sut: BaseViewModel

    @Before
    fun setup() = UiThreadStatement.runOnUiThread {
        sut = BaseViewModel(ApplicationProvider.getApplicationContext())
    }

    //region addLoadingObject
    @Test
    fun addLoadingObject_addOneLoading_oneLoadingDataExisted() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects
        assertEquals(loadingState.size, 1)
    }

    @Test
    fun addLoadingObject_addOneLoading_oneLoadingDataSameValues() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects
        assertTrue(
            loadingState[0].loadingMode == LOADING_MODE_1
            && loadingState[0].tag == TAG_1
        )
    }

    @Test
    fun addLoadingObject_add2LoadingModes_2LoadingDataExisted() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        sut.addLoadingObject(LOADING_MODE_2, TAG_2)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects
        assertEquals(loadingState.size, 2)
    }

    @Test
    fun addLoadingObject_add2LoadingModes_sameFirstValues() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        sut.addLoadingObject(LOADING_MODE_2, TAG_2)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects
        assertTrue(
            loadingState[0].loadingMode == LOADING_MODE_1
            && loadingState[0].tag == TAG_1
        )
    }

    @Test
    fun addLoadingObject_add2LoadingModes_sameSecondValues() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        sut.addLoadingObject(LOADING_MODE_2, TAG_2)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects
        assertTrue(
            loadingState[1].loadingMode == LOADING_MODE_2
            && loadingState[1].tag == TAG_2
        )
    }

    @Test
    fun addLoadingObject_add2LoadingModesWithSameTag_1DataExisted() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        sut.addLoadingObject(LOADING_MODE_2, TAG_1)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects
        assertEquals(loadingState.size, 1)
    }

    @Test
    fun addLoadingObject_add2LoadingModesWithSameTag_LatestLoadingDataExisted() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        sut.addLoadingObject(LOADING_MODE_2, TAG_1)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects

        assertTrue(
            loadingState[0].loadingMode == LOADING_MODE_2
            && loadingState[0].tag == TAG_1
        )
    }

    @Test
    fun addLoadingObject_add1LoadingModeWith2Tags_2LoadingDataExisted() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        sut.addLoadingObject(LOADING_MODE_1, TAG_2)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects
        assertEquals(loadingState.size, 2)
    }

    @Test
    fun addLoadingObject_add1LoadingModeWith2Tags_firstMode1Tag1() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        sut.addLoadingObject(LOADING_MODE_1, TAG_2)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects

        assertTrue(
            loadingState[0].loadingMode == LOADING_MODE_1
            && loadingState[0].tag == TAG_1
        )
    }

    @Test
    fun addLoadingObject_add1LoadingModeWith2Tags_secondMode1Tag2() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        sut.addLoadingObject(LOADING_MODE_1, TAG_2)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects

        assertTrue(
            loadingState[1].loadingMode == LOADING_MODE_1
            && loadingState[1].tag == TAG_2
        )
    }
    //endregion

    //region removeLoadingObject

    @Test
    fun removeLoadingObject_addRemoveOneLoading_noLoadingDataExisted() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        sut.removeLoadingObject(TAG_1)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects
        assertEquals(loadingState.size, 0)
    }

    @Test
    fun removeLoadingObject_add2Remove1Loading_1LoadingDataExisted() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        sut.addLoadingObject(LOADING_MODE_1, TAG_2)
        sut.removeLoadingObject(TAG_1)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects
        assertEquals(loadingState.size, 1)
    }

    @Test
    fun removeLoadingObject_add2Remove1Loading_Tag2Exists() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        sut.addLoadingObject(LOADING_MODE_1, TAG_2)
        sut.removeLoadingObject(TAG_1)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects
        assertEquals(loadingState[0].tag, TAG_2)
    }

    @Test
    fun removeLoadingObject_add2LoadingsWith1TagRemoveTag_noDataExists() = UiThreadStatement.runOnUiThread {
        sut.addLoadingObject(LOADING_MODE_1, TAG_1)
        sut.addLoadingObject(LOADING_MODE_2, TAG_1)
        sut.removeLoadingObject(TAG_1)
        val loadingState = sut.ldLoadingState.value!!.loadingObjects
        assertEquals(loadingState.size, 0)
    }

    //endregion

    //region addAction

    @Test
    fun addAction_add1Action_1ActionExists() = UiThreadStatement.runOnUiThread {
        sut.addAction(ACTION_1)

        assertEquals(sut.ldActions.value?.size, 1)
    }

    @Test
    fun addAction_add1Action_Action1Existed() = UiThreadStatement.runOnUiThread {
        sut.addAction(ACTION_1)

        assertNotNull(sut.ldActions.value?.first{ ACTION_1 == it.action })
    }

    @Test
    fun addAction_add2Action2_2Action2Exists() = UiThreadStatement.runOnUiThread {
        sut.addAction(ACTION_1)
        sut.addAction(ACTION_2)

        assertEquals(sut.ldActions.value?.size, 2)
    }

    @Test
    fun addAction_add2Action2_Action1Existed() = UiThreadStatement.runOnUiThread {
        sut.addAction(ACTION_1)
        sut.addAction(ACTION_2)

        assertNotNull(sut.ldActions.value?.first{ ACTION_1 == it.action })
    }

    @Test
    fun addAction_add2Action2_Action2Existed() = UiThreadStatement.runOnUiThread {
        sut.addAction(ACTION_1)
        sut.addAction(ACTION_2)

        assertNotNull(sut.ldActions.value?.first{ ACTION_2 == it.action })
    }

    //endregion

    companion object {
        const val TAG_1 = "tag.1"
        const val TAG_2 = "tag.2"
        const val LOADING_MODE_1 = 2
        const val LOADING_MODE_2  = 3
        const val ACTION_1 = "ACTION_1"
        const val ACTION_2 = "ACTION_2"
    }
}