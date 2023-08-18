package com.fphoenixcorneae.eyepetizer.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.fragment.app.FragmentActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.permissionx.guolindev.PermissionX

/**
 * 使用PermissionX动态权限请求，全面支持Android 13运行时权限
 */
fun requestPermissionsUsingPermissionX(
    activity: FragmentActivity,
    permissions: List<String>,
    explainMessage: String,
    forwardToSettingsMessage: String,
    explainPositiveText: String = "确定",
    explainNegativeText: String? = "取消",
    forwardToSettingsPositiveText: String = "设置",
    forwardToSettingsNegativeText: String? = "取消",
    onResult: (
        @ParameterName("allGranted") Boolean,
        @ParameterName("grantedList") List<String>,
        @ParameterName("deniedList") List<String>,
    ) -> Unit,
) {
    PermissionX.init(activity)
        .permissions(permissions)
        .onExplainRequestReason { scope, deniedList ->
            scope.showRequestReasonDialog(deniedList, explainMessage, explainPositiveText, explainNegativeText)
        }
        .onForwardToSettings { scope, deniedList ->
            scope.showForwardToSettingsDialog(
                deniedList,
                forwardToSettingsMessage,
                forwardToSettingsPositiveText,
                forwardToSettingsNegativeText
            )
        }
        .request { allGranted, grantedList, deniedList ->
            onResult(allGranted, grantedList, deniedList)
        }
}

/**
 * 使用Accompanist动态权限请求
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissionsUsingAccompanist(
    permissions: List<String>,
    onAllGranted: () -> Unit = {},
) {
    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)
    if (permissionsState.allPermissionsGranted) {
        onAllGranted()
    }
    SideEffect {
        permissionsState.launchMultiplePermissionRequest()
    }
}