package com.rifqi.fitmate.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rifqi.fitmate.R
import com.rifqi.fitmate.ui.theme.neutral10
import com.rifqi.fitmate.ui.theme.neutral30
import com.rifqi.fitmate.ui.theme.neutral80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navigatToEquimentSearch: () -> Unit,
    navigateToExplore: (String) -> Unit
) {

    TopAppBar(
        title = {
            Box(
                Modifier.padding(
                    end = 16.dp

                )
            ) {
                CustomTextField(
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            null,
                            tint = neutral30
                        )
                    },
                    trailingIcon = null,
                    modifier = Modifier
                        .background(
                            neutral80,
                            RoundedCornerShape(percent = 50)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .height(40.dp),
                    fontSize = 14.sp,
                    placeholderText = "Search",
                    navigateToExplore = { value ->
                        navigateToExplore(value)
                    }

                )
            }
        },
        actions = {
            Surface(
                color = neutral80,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.clickable {
                    navigatToEquimentSearch()
                }
            ) {
                Box(Modifier.padding(8.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Flip,
                        tint = neutral10,
                        contentDescription = stringResource(id = R.string.search_equiment)
                    )
                }

            }

        },


        )
}