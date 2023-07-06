package com.zxzx_app.test_compose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zxzx_app.test_compose.ui.theme.*
import com.zxzx_app.test_compose.widget.SuperTextField

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Test_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        LoginPage()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginPage() {

    val fwxy = "《服务协议》"
    val yszc = "《隐私政策》"

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val controller = LocalSoftwareKeyboardController.current

    var uPhone by remember { mutableStateOf("") }
    var uCode by remember { mutableStateOf("") }
    var uPwd by remember { mutableStateOf("") }

    var showPwdView by remember { mutableStateOf(false) }
    var showPwd by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }

    var canClick by remember { mutableStateOf(false) }

    fun checkCanClick() {
        canClick = if (showPwdView) uPhone.isNotEmpty() && uPwd.isNotEmpty()
        else uPhone.isNotEmpty() && uCode.isNotEmpty()
    }

    Image(
        painter = painterResource(id = R.drawable.ic_login_bg),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(550.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(start = 45.dp, end = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(170.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(100.dp, 120.dp)
        )
        Spacer(modifier = Modifier.height(60.dp))
        Box(modifier = Modifier.height(45.dp), contentAlignment = Alignment.CenterEnd) {
            SuperTextField(
                value = uPhone,
                onValueChange = {
                    uPhone = it
                    checkCanClick()
                },
                modifier = Modifier.fillMaxSize(),
                paddingValues = PaddingValues(4.dp), // 设置padding
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color_Black,
                    backgroundColor = Color_T,
                    focusedIndicatorColor = Color_T,
                    unfocusedIndicatorColor = Color_T,
                ),
                textStyle = TextStyle(fontSize = 14.sp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                placeholder = {
                    Text(text = "请输入手机号", fontSize = 14.sp, color = Color_91919C)
                }
            )
            Row(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        uPhone = ""
                        canClick = false
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_del), contentDescription = null,
                    modifier = Modifier.size(13.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color_F2F2F2)
        )
        if (!showPwdView) {
            Spacer(modifier = Modifier.height(15.dp))
            Box(modifier = Modifier.height(45.dp), contentAlignment = Alignment.CenterEnd) {
                SuperTextField(
                    value = uCode,
                    onValueChange = {
                        uCode = it
                        checkCanClick()
                    },
                    modifier = Modifier.fillMaxSize(),
                    paddingValues = PaddingValues(4.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color_Black,
                        backgroundColor = Color_T,
                        focusedIndicatorColor = Color_T,
                        unfocusedIndicatorColor = Color_T,
                    ),
                    textStyle = TextStyle(fontSize = 14.sp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { controller?.hide() }
                    ),
                    placeholder = {
                        Text(text = "请输入验证码", fontSize = 14.sp, color = Color_91919C)
                    }
                )
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .clickable { showToast(context, "获取验证码") },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "获取验证码",
                        fontSize = 12.sp,
                        color = Color_C41335,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color_F2F2F2)
            )
        }
        if (showPwdView) {
            Spacer(modifier = Modifier.height(15.dp))
            Box(modifier = Modifier.height(45.dp), contentAlignment = Alignment.CenterEnd) {
                SuperTextField(
                    value = uPwd,
                    onValueChange = {
                        uPwd = it
                        checkCanClick()
                    },
                    modifier = Modifier.fillMaxSize(),
                    paddingValues = PaddingValues(4.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color_Black,
                        backgroundColor = Color_T,
                        focusedIndicatorColor = Color_T,
                        unfocusedIndicatorColor = Color_T,
                    ),
                    textStyle = TextStyle(fontSize = 14.sp),
                    visualTransformation = if (showPwd) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { controller?.hide() }
                    ),
                    placeholder = {
                        Text(text = "请输入密码", fontSize = 14.sp, color = Color_91919C)
                    }
                )
                Row(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { showPwd = !showPwd },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (showPwd) R.drawable.ic_input_in2 else R.drawable.ic_input_in1
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color_F2F2F2)
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 图片尺寸太大，Icon不展示，害我搞了半天，无语。。。
            Image(
                painter = painterResource(
                    id = if (isChecked) R.drawable.ic_box2 else R.drawable.ic_box1
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(15.dp)
                    .clickable(
                        onClick = { isChecked = !isChecked },
                        // 去除点击效果
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        })
            )
            Spacer(modifier = Modifier.width(5.dp))
            val labTxt = buildAnnotatedString {
                append("同意")
                pushStringAnnotation(fwxy, fwxy)
                withStyle(
                    SpanStyle(
                        color = Color_C41335,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(fwxy)
                }
                pop()
                append("和")
                pushStringAnnotation(yszc, yszc)
                withStyle(
                    SpanStyle(
                        color = Color_C41335,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(yszc)
                }
                pop()
            }
            ClickableText(text = labTxt, onClick = { offset ->
                val list = labTxt.getStringAnnotations(tag = fwxy, offset, offset)
                list.firstOrNull()?.let {
                    showToast(context, "查看$fwxy")
                }
                val list2 = labTxt.getStringAnnotations(tag = yszc, offset, offset)
                list2.firstOrNull()?.let {
                    showToast(context, "查看$yszc")
                }
            })
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                if (isChecked) {
                    showToast(context, "登录成功")
                } else {
                    showToast(context, "请同意${fwxy}和${yszc}")
                }
            },
            enabled = canClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color_C41335,
                contentColor = Color_White,
                disabledBackgroundColor = Color_FFCDCD
            ),
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {
            Text(text = "登录", color = Color_White, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = if (showPwdView) "切换验证码登录" else "切换为密码登录",
            color = Color_91919C,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .clickable(
                    onClick = {
                        showPwdView = !showPwdView
                        checkCanClick()
                    },
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    })
        )
        Spacer(modifier = Modifier.height(25.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Spacer(
                modifier = Modifier
                    .height(0.5.dp)
                    .fillMaxWidth()
                    .background(Color_F2F2F2)
            )
            Text(
                text = "第三方登录",
                color = Color_91919C,
                fontSize = 12.sp,
                modifier = Modifier
                    .background(Color_White)
                    .padding(start = 10.dp, end = 10.dp)

            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_share_qq),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        onClick = {
                            if (isChecked) {
                                showToast(context, "QQ登录")
                            } else {
                                showToast(context, "请同意${fwxy}和${yszc}")
                            }
                        },
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    )
            )
            Spacer(modifier = Modifier.width(40.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_share_wechat),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        onClick = {
                            if (isChecked) {
                                showToast(context, "微信登录")
                            } else {
                                showToast(context, "请同意${fwxy}和${yszc}")
                            }
                        },
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    )
            )
        }
    }
}

private fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    Test_composeTheme {
        LoginPage()
    }
}