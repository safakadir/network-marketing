<template>
<div class="content-container rounded" id="home">

    <home-welcome v-on="$listeners" />

    <div class="section mb-0">
        <div class="container">
            <div class="row">
                <div class="col-12 offset-0 col-lg-8 offset-lg-2 text-center">
                <h2>Yaşamınızı değiştirin, hedeflerinizi belirleyin, fırsatları kaçırmayın.</h2>
                <p>Yenilikçi ürünleri, hiç görülmemiş kazanç planı ve kendini ispatlamış, doğrudan satış iş modeli kullanan **** NETWORK, hızla büyüyen ve global olarak da zirveyi hedefleyen bir şirket olarak Network Marketing pazarına yeni bir yön vermeyi hedefliyor. Tüm müşteri ve bayileri için sürdürülebilir ve değerli fırsatlar yaratmak için en parlak fikirleri, en iyi profesyonelleri ve yeni teknolojileri kullanıyor.
                    <br/><strong>Siz de gelin bu ağın bir parçası olun ve hemen kazanmaya başlayın!</strong>
                </p>
                </div>
            </div>
        </div>
    </div>

    <urun-list class="p-5" onecikan />
</div>
</template>

<script>

import Welcome from '../../containers/home/Welcome'
import Footer from '../../containers/home/Footer'
import UrunList from '../../containers/home/UrunList'

export default {

    components: {
        'home-welcome': Welcome,
        'home-footer': Footer,
        'urun-list': UrunList,
    },
    data() { return {
    }},
}
</script>
