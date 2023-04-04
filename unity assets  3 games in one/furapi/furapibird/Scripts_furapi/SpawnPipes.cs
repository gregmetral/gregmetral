using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpawnPipes : MonoBehaviour
{
    protected float timer = 2f;
    [SerializeField] protected GameObject green_pipe;
    [SerializeField] protected GameObject red_pipe;
    [SerializeField] protected GameObject shark;
    [SerializeField] protected GameObject ref_tmp;
    protected int pipeCount = 0;
    protected float timer_shark = 5f;
    protected int shark_count = 0;
    protected bool isTextShown = false;


    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if(bird.restart == true)
        {
            pipeCount = 0;
            timer_shark = 5f;
            shark_count = 0;
            timer = 2f;
            isTextShown = false;
        }

        if (pipeCount != 10)
        {
            timer -= Time.deltaTime;
            if (timer < 0)
            {
                timer = 2f;
                pipeCount++;
                float posY = Random.Range(-2.2f, 2.2f);
                GameObject new_green = Instantiate(green_pipe);
                new_green.transform.position = new Vector3(10.5f, posY, 0);
            }
        }
        
        if (pipeCount == 10)
        {
            if (isTextShown == false)
            {
                StartCoroutine(Attack());
                isTextShown = true;
            }

            if (shark_count != 10)
            {
                timer_shark -= Time.deltaTime;
                if (timer_shark < 0)
                {
                    timer_shark = 0.7f;
                    float posY = Random.Range(-4f, 4f);
                    GameObject new_shark = Instantiate(shark);
                    new_shark.transform.position = new Vector3(10.5f, posY, 0);
                    shark_count++;
                }
            } else if(shark_count == 10)
            {
                shark_count = 0;
                pipeCount = 0;
                timer_shark = 5f;
                isTextShown = false;
            }

           
        }
        
    }

    protected IEnumerator Attack() 
    {
        GameObject new_text = Instantiate(ref_tmp);
        yield return new WaitForSeconds(5);
        Destroy(new_text);
    }

}
