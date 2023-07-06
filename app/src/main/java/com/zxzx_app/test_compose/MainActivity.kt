package com.zxzx_app.test_compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zxzx_app.test_compose.ui.theme.Test_composeTheme

/**
 * 参考：https://stars-one.site/2021/08/22/jetpack-compose-study-2
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Test_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        ToolBar()
                        Spacer(Modifier.height(10.dp)) // 空布局？ 类似Space，用作 margin
                        TestTextView(name = "submit")
                        Spacer(Modifier.height(10.dp))
                        TextView2()
                        Spacer(Modifier.height(10.dp))
                        Label()
                        Spacer(Modifier.height(10.dp))
                        Layout()
                        Spacer(Modifier.height(10.dp))
                        TestButton()
                    }
                }
            }
        }
    }
}

@Composable
fun ToolBar() {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(50.dp)
            .background(color = Color.Yellow),
        verticalAlignment = Alignment.CenterVertically, // 垂直方向的对齐方式
        horizontalArrangement = Arrangement.SpaceBetween // 水平方向的对齐方式  类似Flex布局
    ) {

        IconButton(onClick = { showToast(context, "返回") }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = null)
        }
        Text(text = "我是标题", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = { showToast(context, "更多") }) {
            Icon(Icons.Filled.Menu, contentDescription = null)
        }
    }
}

@Composable
fun TestTextView(name: String) {
    Text(
        text = "I'm $name".repeat(30), // 重复30次
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 20.sp,
        color = Color.Blue
    )
}

/*
文本 -> stringResource(R.string.app_name)
颜色 -> colorResource(R.color.black)
尺寸 -> dimensionResource(R.dimen.padding_small)
图片 -> painterResource(R.drawable.ic_logo)
 */
// 富文本展示
@Composable
fun TextView2() {
    val context = LocalContext.current
    val URL = "https://www.baidu.com"
    val labTxt = buildAnnotatedString {
        append("我是一个粉刷匠")
        // 设置存放的数据和标签
        pushStringAnnotation(URL, URL)
        withStyle(
            SpanStyle(
                color = colorResource(id = R.color.purple_500),
                fontSize = 20.sp,
            )
        ) {
            append("浏览器查看")
        }
        pop() // 需要调用 pop 代表结束
        withStyle(
            SpanStyle(
                color = Color.Red,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("警告！！！")
        }
        withStyle(ParagraphStyle()) {
            append("我是换行展示")
        }
    }
    ClickableText(text = labTxt, onClick = { offset ->
        val list = labTxt.getStringAnnotations(tag = URL, offset, offset)
        list.firstOrNull()?.let {
            showToast(context, it.item)
        }
    })
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Label() {

    val context = LocalContext.current

    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var text3 by remember { mutableStateOf("") }
    var showPwd by remember { mutableStateOf(false) }

    // 控制输入框焦点？？？
    val focusManager = LocalFocusManager.current
    // 控制输入法显示、隐藏
    val controller = LocalSoftwareKeyboardController.current

    Column {
        TextField(
            value = text1,
            onValueChange = { text1 = it },
            colors = TextFieldDefaults.textFieldColors( // 颜色
                textColor = Color.Black, // 输入的文字颜色
                focusedLabelColor = Color.Red,
                unfocusedLabelColor = Color.Black
            ),
            label = {
                Text(text = "请输入邮箱")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            leadingIcon = { // 输入框 左侧显示内容
                Icon(Icons.Filled.Email, null)
            },
            trailingIcon = { // 输入框 右侧显示内容
                Text(text = "@163.com", color = Color.Blue)
            }
        )
        Spacer(Modifier.height(10.dp))
        TextField(
            value = text2,
            onValueChange = { text2 = it },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black, // 输入的文字颜色
                backgroundColor = Color.Transparent, // 输入框的背景颜色
                focusedIndicatorColor = Color.Red, // 输入框处于焦点时，底部指示器的颜色
                unfocusedIndicatorColor = Color.Black, // 输入框失去焦点时，底部指示器的颜色
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            placeholder = {
                Text(text = "请输入用户名")
            }
        )
        Spacer(Modifier.height(10.dp))
        Box(
            Modifier
                .fillMaxWidth(1f) // 0-1f
                .height(50.dp)
                .padding(start = 13.dp, end = 13.dp)
                .border(1.dp, Color.Green, RoundedCornerShape(25.dp))
                .background(Color.Gray, RoundedCornerShape(25.dp))
        ) {
            TextField(
                value = text3,
                onValueChange = { text3 = it },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true,
                placeholder = {
                    Text(text = "请输入密码", fontSize = 14.sp)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    showToast(context, text3)
                    controller?.hide()
                }),
                visualTransformation = if (showPwd) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPwd = !showPwd }) {
                        Icon(
                            painterResource(id = if (showPwd) R.drawable.ic_input_in2 else R.drawable.ic_input_in1),
                            null
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun Layout() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Cyan),
        contentAlignment = Alignment.Center // 子项对齐方式
    ) {
        Text("左上", modifier = Modifier.align(Alignment.TopStart)) // 改变子项的对齐方式
        Text("右上", modifier = Modifier.align(Alignment.TopEnd))
        Text("居中")
        Text("左下", modifier = Modifier.align(Alignment.BottomStart))
        Text("右下", modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
fun TestButton(name: String = "立即登录") {
    val context = LocalContext.current
    var i by remember { mutableStateOf(0) }
    Button(
        onClick = {
            i++
            context.startActivity(Intent(context, LoginActivity::class.java))
        },
        modifier = Modifier
            .fillMaxWidth(1f) // 0-1f
            .height(50.dp)
            .padding(start = 13.dp, end = 13.dp),
        elevation = ButtonDefaults.elevation(4.dp, 10.dp, 0.dp), // 默认阴影、按下阴影、禁用阴影
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Yellow, // 背景颜色
            contentColor = Color.Black // 内容颜色
        ),
        shape = RoundedCornerShape( // 形状
            topStart = 0.dp,
            topEnd = 10.dp,
            bottomStart = 10.dp,
            bottomEnd = 0.dp
        ),
        border = BorderStroke(1.dp, Color.Blue) // 边框
    ) {
        Text(text = "$name $i")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Column {
        TestTextView(name = "submit")
        Spacer(Modifier.height(10.dp))
        TextView2()
        Spacer(Modifier.height(10.dp))
        Label()
        Spacer(Modifier.height(10.dp))
        TestButton()
    }
}

private fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}