package com.zxzx_app.test_compose

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 参考：https://stars-one.site/2021/08/22/jetpack-compose-study-2
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*Test_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TestTextView("Bob and I'm 18 years old")
                }
            }*/
            Column {
                TestTextView(name = "submit")
                Box(Modifier.height(10.dp))
                TextView2(this@MainActivity)
                Box(Modifier.height(10.dp))
                Label()
                Box(Modifier.height(10.dp))
                TestButton()
            }
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
fun TextView2(context: Context) {
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
            Toast.makeText(context, it.item, Toast.LENGTH_SHORT).show()
        }
    })
}

@Composable
fun Label() {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var text3 by remember { mutableStateOf("") }
    var showPwd by remember { mutableStateOf(false) }
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
            leadingIcon = { // 输入框 左侧显示内容
                Icon(Icons.Filled.Email, null)
            },
            trailingIcon = { // 输入框 右侧显示内容
                Text(text = "@163.com", color = Color.Blue)
            }
        )
        Box(Modifier.height(10.dp))
        TextField(
            value = text2,
            onValueChange = { text2 = it },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black, // 输入的文字颜色
                backgroundColor = Color.Transparent, // 输入框的背景颜色
                focusedIndicatorColor = Color.Red, // 输入框处于焦点时，底部指示器的颜色
                unfocusedIndicatorColor = Color.Black, // 输入框失去焦点时，底部指示器的颜色
            ),
            placeholder = {
                Text(text = "请输入用户名")
            }
        )
        Box(Modifier.height(10.dp))
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
fun TestButton(name: String = "提交") {
    var i by remember { mutableStateOf(0) }
    Button(
        onClick = {
            i++
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
    /*Test_composeTheme {
        Greeting("Android")
    }*/
    Column {
        TestTextView(name = "submit")
        TestButton()
    }
}