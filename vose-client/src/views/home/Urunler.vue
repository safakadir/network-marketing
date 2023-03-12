<template>
<main class="sideless-main">
<div class="container-fluid">
    <div>
        <b-row>
            <b-colxx xxs="12" class="rounded">
                <urun-list class="px-5 py-4" />
            </b-colxx>
        </b-row>
    </div>
</div>
</main>
</template>

<script>
import UrunList from '../../containers/home/UrunList'

export default {
    
    components: {
        'urun-list': UrunList,
    },
    data() { return {
    }},
}
</script>