package com.limon.adnova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val OR = Color(0xFFFFC14A) to Color(0xFFFF6A2B)
val BL = Color(0xFF5CC8FF) to Color(0xFF2563FF)
val GR = Color(0xFF6DFFB0) to Color(0xFF10B45A)
val PK = Color(0xFFFF8AD0) to Color(0xFFC026D3)
val VI = Color(0xFFB79BFF) to Color(0xFF6A35FF)
val PAL = listOf(OR, BL, GR, PK, VI)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }
    }
}

@Composable
fun App() {
    var tab by remember { mutableStateOf(0) }
    Bg {
        Column(Modifier.fillMaxSize().statusBarsPadding()) {
            Box(Modifier.weight(1f)) {
                when (tab) {
                    0 -> Home()
                    1 -> BoostScreen()
                    2 -> Inbox()
                    3 -> Reports()
                    else -> Settings()
                }
            }
            NavBar(tab) { tab = it }
        }
    }
}

@Composable
fun Bg(content: @Composable BoxScope.() -> Unit) = Box(
    Modifier.fillMaxSize().background(Color(0xFF150F38)).drawBehind {
        fun orb(c: Long, x: Float, y: Float, r: Float) {
            val o = Offset(size.width * x, size.height * y)
            drawCircle(Brush.radialGradient(listOf(Color(c), Color.Transparent), o, r), r, o)
        }
        orb(0xFFFF4F9A, .1f, .05f, 600f)
        orb(0xFF18C8FF, .95f, .22f, 650f)
        orb(0xFF7A4DFF, .1f, .6f, 800f)
        orb(0xFF14E0A8, .9f, .9f, 650f)
    },
    content = content
)

fun Modifier.glass(r: Int = 24): Modifier = this
    .clip(RoundedCornerShape(r.dp))
    .background(Brush.linearGradient(listOf(Color.White.copy(.26f), Color.White.copy(.07f))))
    .border(1.dp, Brush.linearGradient(listOf(Color.White.copy(.6f), Color.White.copy(.12f))), RoundedCornerShape(r.dp))

@Composable
fun T(s: String, sz: Int = 14, w: FontWeight = FontWeight.Medium, a: Float = 1f) =
    Text(s, color = Color.White.copy(a), fontSize = sz.sp, fontWeight = w)

@Composable
fun Ic(e: String, c: Pair<Color, Color>, size: Int = 44) = Box(
    Modifier.size(size.dp).clip(RoundedCornerShape(15.dp)).background(Brush.linearGradient(listOf(c.first, c.second))),
    contentAlignment = Alignment.Center
) { Text(e, fontSize = (size * .5).sp) }

@Composable
fun NavBar(sel: Int, onSel: (Int) -> Unit) {
    val items = listOf("🏠" to "Home", "🚀" to "Boost", "💬" to "Inbox", "📊" to "Reports", "⚙️" to "Settings")
    Row(Modifier.navigationBarsPadding().padding(14.dp).fillMaxWidth().glass(30).padding(6.dp)) {
        items.forEachIndexed { i, (e, l) ->
            Column(
                Modifier.weight(1f).clip(RoundedCornerShape(22.dp))
                    .background(if (i == sel) Color.White.copy(.25f) else Color.Transparent)
                    .clickable { onSel(i) }.padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(e, fontSize = 21.sp)
                T(l, 11)
            }
        }
    }
}

@Composable
fun RowScope.Stat(e: String, c: Pair<Color, Color>, v: String, l: String) =
    Row(Modifier.weight(1f).glass(20).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Ic(e, c)
        Spacer(Modifier.width(10.dp))
        Column {
            T(v, 19, FontWeight.ExtraBold)
            T(l, 12, a = .85f)
        }
    }

@Composable
fun BoostRow(n: String, p: Float, s: String) = Column(Modifier.fillMaxWidth().glass(20).padding(14.dp)) {
    T(n, 14, FontWeight.Bold)
    Box(Modifier.padding(vertical = 8.dp).fillMaxWidth().height(6.dp).clip(RoundedCornerShape(6.dp)).background(Color.White.copy(.22f))) {
        Box(
            Modifier.fillMaxWidth(p).fillMaxHeight()
                .background(Brush.horizontalGradient(listOf(Color(0xFF14E0A8), Color(0xFF18C8FF))))
        )
    }
    T(s, 12, a = .85f)
}

@Composable
fun Home() = LazyColumn(
    Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
) {
    item {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(Modifier.glass(30).padding(6.dp, 6.dp, 14.dp, 6.dp), verticalAlignment = Alignment.CenterVertically) {
                Ic("f", Color(0xFF4AA3FF) to Color(0xFF1457E6), 32)
                Spacer(Modifier.width(8.dp))
                Column {
                    T("Rafi Store", 14, FontWeight.Bold)
                    T("Facebook page", 11, a = .8f)
                }
            }
            Box(Modifier.size(44.dp).glass(22), Alignment.Center) { Text("🔔", fontSize = 20.sp) }
        }
    }
    item {
        Column(Modifier.fillMaxWidth().glass().padding(20.dp)) {
            T("Total spent this month", 13, a = .85f)
            T("\$1,284.50", 40, FontWeight.ExtraBold)
            Spacer(Modifier.height(10.dp))
            Row(
                Modifier.fillMaxWidth().height(78.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                listOf(.42f, .58f, .36f, .72f, .62f, .86f, .98f).forEach {
                    Box(
                        Modifier.weight(1f).fillMaxHeight(it)
                            .clip(RoundedCornerShape(8.dp, 8.dp, 4.dp, 4.dp))
                            .background(Brush.verticalGradient(listOf(Color(0xFF9DF3FF), Color(0xFF8A6BFF))))
                    )
                }
            }
        }
    }
    item {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Stat("🚀", OR, "8", "Active boosts")
                Stat("👁️", BL, "48.2K", "Reached")
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Stat("👆", GR, "3,904", "Clicks")
                Stat("💬", PK, "27", "Messages")
            }
        }
    }
    item { T("Quick actions", 16, FontWeight.Bold) }
    item {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf(
                Triple("🚀", "New boost", OR), Triple("🎬", "Upload", PK),
                Triple("📱", "WhatsApp", GR), Triple("📈", "Reports", VI)
            ).forEach { (e, l, c) ->
                Column(
                    Modifier.weight(1f).glass(20).padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Ic(e, c)
                    T(l, 11)
                }
            }
        }
    }
    item { T("Running now", 16, FontWeight.Bold) }
    items(listOf(
        Triple("Eid collection reel", .68f, "\$34.00 of \$50.00"),
        Triple("Free delivery offer", .35f, "\$17.50 of \$50.00")
    )) { (n, p, s) -> BoostRow(n, p, s) }
}

@Composable
fun ListScreen(title: String, header: @Composable () -> Unit = {}, rows: List<Triple<String, String, String>>) =
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { T(title, 24, FontWeight.ExtraBold) }
        item { header() }
        itemsIndexed(rows) { i, (e, t, s) ->
            Row(Modifier.fillMaxWidth().glass(20).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Ic(e, PAL[i % 5])
                Spacer(Modifier.width(12.dp))
                Column {
                    T(t, 14, FontWeight.Bold)
                    T(s, 12, a = .8f)
                }
            }
        }
    }

@Composable
fun BoostScreen() = ListScreen("Boost", rows = listOf(
    Triple("🎬", "Eid collection reel", "Active · \$34.00 of \$50.00"),
    Triple("🛍️", "Free delivery offer", "Active · \$17.50 of \$50.00"),
    Triple("📸", "New arrival photos", "Paused · \$12.00 of \$30.00"),
    Triple("✅", "Ramadan sale post", "Completed · \$80.00")
))

@Composable
fun Inbox() {
    var messenger by remember { mutableStateOf(true) }
    ListScreen(
        "Inbox",
        header = {
            Row(Modifier.fillMaxWidth().glass(22).padding(4.dp)) {
                listOf("Messenger", "WhatsApp").forEachIndexed { i, n ->
                    Box(
                        Modifier.weight(1f).clip(RoundedCornerShape(18.dp))
                            .background(if ((i == 0) == messenger) Color.White.copy(.28f) else Color.Transparent)
                            .clickable { messenger = i == 0 }.padding(10.dp),
                        Alignment.Center
                    ) { T(n, 14, FontWeight.Bold) }
                }
            }
        },
        rows = if (messenger) listOf(
            Triple("👤", "Karim", "দাম কত ভাই?"),
            Triple("👤", "Nusrat", "ডেলিভারি কবে পাব?"),
            Triple("👤", "Sohel", "Size M আছে?")
        ) else listOf(
            Triple("👤", "Rahim", "অর্ডার কনফার্ম করুন"),
            Triple("👤", "Mitu", "ছবি পাঠিয়েছি")
        )
    )
}

@Composable
fun Reports() = ListScreen("Reports", rows = listOf(
    Triple("📅", "Today", "\$86.20 spent"),
    Triple("🗓️", "This week", "\$412.75 spent"),
    Triple("📆", "This month", "\$1,284.50 spent"),
    Triple("📘", "Rafi Store", "\$940.10 total"),
    Triple("🛒", "Limon Shop", "\$344.40 total")
))

@Composable
fun Settings() = ListScreen("Settings", rows = listOf(
    Triple("📘", "Connected pages", "Add or remove Facebook pages"),
    Triple("📱", "WhatsApp Business", "Not connected"),
    Triple("💳", "Billing", "Payment methods and invoices"),
    Triple("🔔", "Notifications", "Boost and message alerts"),
    Triple("🌐", "Language", "বাংলা / English")
))
