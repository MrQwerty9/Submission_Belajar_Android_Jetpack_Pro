package com.sstudio.submissionbajetpackpro.utils

import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.data.MovieTvEntity
import java.util.*

object DataDummy {

    fun generateDummyMovies(): ArrayList<MovieTvEntity> {

        val movies = ArrayList<MovieTvEntity>()

        movies.add(MovieTvEntity(1,
                "Venom",
                "Jurnalis investigasi Eddie Brock berusaha untuk kembali setelah skandal, tetapi tidak sengaja menjadi Tuan rumah Venom, seorang simbiot alien yang sangat kuat dan sakti. Segera, ia harus bergantung pada kekuatan yang baru ditemukannya melindungi dunia dari organisasi bayangan mencari simbiosis mereka sendiri.",
                R.drawable.poster_venom,
                )
        )
        movies.add(MovieTvEntity(2,
            "Spider-Man: Into the Spider-Verse",
            "Miles Morales menyulap hidupnya antara menjadi siswa sekolah menengah dan menjadi manusia laba-laba. Ketika Wilson\"Kingpin\" Fisk menggunakan collider super, yang lain dari seberang Spider-Verse diangkut ke dimensi ini.",
            R.drawable.poster_spiderman,
        )
        )
        movies.add(MovieTvEntity(3,
            "How to Train Your Dragon: The Hidden World",
            "Saat Hiccup memenuhi mimpinya untuk menciptakan utopia naga yang damai, penemuan Toothless yang liar, pasangan yang sulit ditangkap menarik Night Fury. Ketika bahaya meningkat di rumah dan pemerintahan Hiccup sebagai kepala desa diuji, baik naga dan pengendara harus membuat keputusan yang mustahil untuk menyelamatkan jenis mereka.",
            R.drawable.poster_dragon,
        )
        )
        movies.add(MovieTvEntity(4,
            "Aquaman",
            "Setelah rumah bagi peradaban paling maju di Bumi, Atlantis sekarang merupakan kerajaan bawah laut yang diperintah oleh Raja Orm yang haus kekuasaan. Dengan pasukan yang sangat besar, Orm berencana untuk menaklukkan orang-orang samudera yang tersisa dan kemudian dunia permukaan. Yang menghalangi jalannya adalah Arthur Curry, saudara setengah manusia Orm, setengah saudara Atlantis dan pewaris sejati takhta.",
            R.drawable.poster_aquaman,
        )
        )
        movies.add(MovieTvEntity(5,
            "Avengers: Infinity War",
            "Ketika Avengers dan sekutu mereka terus melindungi dunia dari ancaman yang terlalu besar bagi siapa pun Pahlawan yang harus ditangani, bahaya baru telah muncul dari bayang-bayang kosmik: Thanos. Seorang lalim keburukan intergalaksi, tujuannya adalah untuk mengumpulkan semua enam Batu Infinity, artefak dari kekuatan yang tak terbayangkan, dan menggunakannya untuk menimbulkan keinginan bengkok pada semua realitas. Segala yang Avengers telah perjuangkan telah mengarah ke saat ini - nasib Bumi dan keberadaannya sendiri tidak pernah lebih pasti.",
            R.drawable.poster_avengerinfinity,
        )
        )
        movies.add(MovieTvEntity(6,
            "Bumblebee",
            "Dalam pelarian pada tahun 1987, Bumblebee menemukan tempat perlindungan di tempat barang rongsokan di kota pantai kecil California. Charlie, di puncak usia 18 dan mencoba menemukan tempatnya di dunia, menemukan Bumblebee, pertempuran-bekas luka dan rusak. Ketika Charlie menghidupkannya, dia dengan cepat mengetahui bahwa ini bukan bug VW kuning biasa.",
            R.drawable.poster_bumblebee,
        )
        )

        movies.add(MovieTvEntity(7,
            "Creed II",
            "Antara kewajiban pribadi dan pelatihan untuk pertarungan besar berikutnya melawan lawan dengan ikatannya masa lalu keluarga, Adonis Creed menghadapi tantangan hidupnya.",
            R.drawable.poster_creed,
        )
                )
        movies.add(MovieTvEntity(8,
            "Once Upon a Deadpool",
            "Fred Savage yang diculik dipaksa untuk menahan Deadpool PG-13 rendition Deadpool 2 sebagai kisah Putri Pengantin yang penuh keajaiban, heran nol F.",
            R.drawable.poster_deadpool,
        )
        )
        movies.add(MovieTvEntity(9,
            "Glass",
            "Dalam serangkaian pertemuan yang meningkat, mantan penjaga keamanan David Dunn menggunakan kemampuan supernaturalnya untuk melacak Kevin Wendell Crumb, seorang lelaki terganggu yang memiliki dua puluh empat kepribadian. Sementara itu, kehadiran bayangan Elia Price muncul sebagai seorang orkestra yang menyimpan rahasia penting bagi keduanya.",
            R.drawable.poster_glass,
        )
        )
        movies.add(MovieTvEntity(10,
            "Preman Pensiun",
            "Gobang kembali ke Bandung, mengundang teman-temannya, para mantan preman untuk bertemu kembali. Mereka yang diundang adalah Ujang, Murad, Pipit, Cecep, Bohim, Mang Uu, Dikdik dan Joni. Dikdik kemudian tidak jadi datang karena masalah dengan istrinya. Dalam pertemuan yang disebut “reuni kecil-kecilan” itu, Gobang meminta bantuan teman-temannya untuk melakukan penyelidikan, siapa pelaku pembantaian terhadap adik iparnya hingga tewas. Pelaku pembantaian kemudian diketahui adalah Darman dan kawan-kawan yang hanya sebagai orang suruhan. Dalang di balik kejadian itu ternyata teman mereka sendiri, sesama bekas anak buah Muslihat (Epy Kusnandar) yang sebenarnya juga sudah pensiun. Muslihat mencoba mencegah terjadinya perang saudara, tapi Gobang sudah terlanjur datang dengan membawa dendam, “darah dibayar darah, nyawa dibayar nyawa",
            R.drawable.poster_preman,
        )
        )
        return movies
    }

    fun generateDummyTv(): ArrayList<MovieTvEntity> {

        val tv = ArrayList<MovieTvEntity>()

        tv.add(MovieTvEntity(1,
            "Arrow",
            "Playboy miliarder manja, Oliver Queen, hilang dan diduga tewas ketika kapal pesiarnya hilang di laut. Dia kembali lima tahun kemudian seorang pria yang berubah, bertekad untuk membersihkan kota sebagai main hakim sendiri bersenjatakan busur.",
            R.drawable.poster_arrow,
        )
        )

        tv.add(MovieTvEntity(2,
            "The Flash",
            "Setelah akselerator partikel menyebabkan badai hebat, Penyelidik CSI Barry Allen disambar petir dan jatuh koma. Beberapa bulan kemudian dia terbangun dengan kekuatan kecepatan super, memberinya kemampuan untuk bergerak melalui Central City seperti malaikat penjaga yang tak terlihat. Meskipun awalnya senang dengan kekuatan barunya, Barry terkejut menemukan bahwa dia bukan satu-satunya \"manusia meta\" yang diciptakan setelah ledakan akselerator - dan tidak semua orang menggunakan kekuatan baru mereka untuk kebaikan. Barry bermitra dengan S.T.A.R. Lab dan mendedikasikan hidupnya untuk melindungi yang tidak bersalah. Untuk saat ini, hanya beberapa teman dekat dan rekan yang tahu bahwa Barry secara harfiah adalah manusia tercepat, tetapi tidak lama sebelum dunia mengetahui apa yang menjadi Barry Allen. The Flash.",
            R.drawable.poster_flash,
        )
        )
        tv.add(MovieTvEntity(3,
            "Doom Patrol",
            "Anggota Patroli Doom masing-masing mengalami kecelakaan mengerikan yang memberi mereka kemampuan manusia super - tetapi juga membuat mereka terluka dan cacat. Trauma dan tertindas, tim menemukan tujuan melalui Ketua, yang menyatukan mereka untuk menyelidiki fenomena paling aneh yang ada - dan untuk melindungi Bumi dari apa yang mereka temukan.",
            R.drawable.poster_doom_patrol,
        )
        )
        tv.add(MovieTvEntity(4,
            "Game of Thrones",
            "Tujuh keluarga bangsawan berjuang untuk menguasai tanah mitos Westeros. Gesekan antara rumah-rumah mengarah ke perang skala penuh. Semua sementara kejahatan yang sangat kuno terbangun di utara terjauh. Di tengah-tengah perang, perintah militer yang diabaikan, Night\\'s Watch, adalah yang berdiri di antara alam manusia dan kengerian es di luar sana.",
            R.drawable.poster_god,
        )
        )
        tv.add(MovieTvEntity(5,
            "Gotham",
            "Sebelum ada Batman, ada GOTHAM. Semua orang tahu nama Komisaris Gordon. Dia adalah salah satu musuh terbesar dunia kejahatan, seorang pria yang reputasinya identik dengan hukum dan ketertiban. Tapi apa yang diketahui tentang kisah Gordon dan kenaikannya dari detektif pemula ke Komisaris Polisi? Apa yang diperlukan untuk menavigasi berbagai lapisan korupsi yang diam-diam memerintah Kota Gotham, tempat bertelurnya penjahat paling ikonik di dunia? Dan keadaan apa yang menciptakan mereka - persona yang lebih besar dari kehidupan yang akan menjadi Catwoman, The Penguin, The Riddler, Two-Face dan The Joker?",
            R.drawable.poster_gotham,
        )
        )
        tv.add(MovieTvEntity(6,
            "NCIS",
            "Dari pembunuhan dan spionase hingga terorisme dan kapal selam curian, tim agen khusus menyelidiki kejahatan apa pun yang memiliki sedikit bukti yang terhubung dengan personel Korps Angkatan Laut dan Korps Marinir, terlepas dari pangkat atau jabatannya.",
            R.drawable.poster_ncis,
        )
        )
        tv.add(MovieTvEntity(7,
            "Riverdale",
            "Berada di masa kini, seri ini menawarkan pandangan subversif yang berani tentang Archie, Betty, Veronica dan teman-teman mereka, menjelajahi kejernihan kehidupan kota kecil, kegelapan dan keanehan yang menggelegak di bawah fasad sehat Riverdale.",
            R.drawable.poster_riverdale,
        )
        )
        tv.add(MovieTvEntity(8,
            "The Simpsons",
            "Bertempat di Springfield, kota rata-rata di Amerika, pertunjukan ini berfokus pada kejenakaan dan petualangan sehari-hari keluarga Simpson; Homer, Marge, Bart, Lisa dan Maggie, serta ribuan pemain virtual. Sejak awal, serial ini telah menjadi ikon budaya pop, menarik ratusan selebriti menjadi bintang tamu. Acara ini juga menjadi terkenal karena satirnya yang tak kenal takut terhadap kehidupan politik, media, dan Amerika secara umum.",
            R.drawable.poster_the_simpson,
        )
        )
        tv.add(MovieTvEntity(9,
            "The Umbrella Academy",
            "Keluarga superhero yang disfungsional berkumpul untuk memecahkan misteri kematian ayah mereka, ancaman kiamat, dan banyak lagi.",
            R.drawable.poster_the_umbrella,
        )
        )
        tv.add(MovieTvEntity(10,
            "The Walking Dead",
            "Wakil Sheriff Rick Grimes terbangun dari koma untuk menemukan dunia pasca-apokaliptik yang didominasi oleh zombie pemakan daging. Dia berangkat untuk menemukan keluarganya dan bertemu dengan banyak penyintas lainnya di sepanjang jalan.",
            R.drawable.poster_the_walking_dead,
        )
        )
        return tv
    }
}